package com.huojumu.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greendao.gen.NativeOrdersDao;
import com.huojumu.MyApplication;
import com.huojumu.model.BaseBean;
import com.huojumu.model.NativeOrders;
import com.huojumu.model.OrderInfo;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPosService extends Service {

    private List<NativeOrders> nativeOrdersList;
    private List<OrderInfo> uploadStrs = new ArrayList<>();
    private NativeOrdersDao ordersDao;
    private Timer timer;
    private Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        ordersDao = ((MyApplication) getApplication()).getDaoSession().getNativeOrdersDao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLoop();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
        timer = null;
        ordersDao = null;
        nativeOrdersList = null;
        sendBroadcast(new Intent(Constant.SERVICE_ACTION));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 轮询数据库，间隔上传
     */
    private void startLoop() {
        timer = new Timer();
        //间隔1小时轮巡一次数据库
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //联网状态下去轮询数据库
                if (MyApplication.getSocketTool().isAlive()) {
                    nativeOrdersList = ordersDao.loadAll();
                    if (nativeOrdersList != null && nativeOrdersList.size() > 0) {
                        for (int i = 0; i < nativeOrdersList.size(); i++) {
                            uploadStrs.add((OrderInfo) gson.fromJson(nativeOrdersList.get(i).getOrderJson(), new TypeToken<OrderInfo>() {
                            }.getType()));
                        }
                        //如果数据库中有存入的本地订单、则开始上传
                        NetTool.orderBatch(PrinterUtil.toJson(uploadStrs), new GsonResponseHandler<BaseBean<String>>() {
                            @Override
                            public void onSuccess(int statusCode, BaseBean<String> response) {
                                //上传成功后清空数据库
                                if (response.getCode().equals("0")) {
                                    ordersDao.deleteAll();
                                }
                                uploadStrs.clear();
                            }

                            @Override
                            public void onFailure(int statusCode, String code, String error_msg) {
                                uploadStrs.clear();
                            }
                        });
                    }
                }
            }
        }, 10000, 60 * 60 * 1000);
    }

}
