package com.huojumu.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.greendao.gen.NativeOrdersDao;
import com.huojumu.MyApplication;
import com.huojumu.model.BaseBean;
import com.huojumu.model.NativeOrders;
import com.huojumu.model.OrderBack;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPosService extends Service {

    private List<NativeOrders> nativeOrdersList;
    private NativeOrdersDao ordersDao;
    private Timer timer;

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
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 轮询数据库，间隔上传
     */
    private void startLoop(){
        timer = new Timer();
        //间隔10分钟轮询一次数据库
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //联网状态下去轮询数据库
                if (MyApplication.getSocketTool().isAlive()) {
                    Log.e("service", "run: ");
                    nativeOrdersList = ordersDao.loadAll();
                    if (nativeOrdersList != null && !nativeOrdersList.isEmpty()) {
                        //如果数据库中有存入的本地订单、则开始上传
                        NetTool.orderBatch(PrinterUtil.toJson(nativeOrdersList), new GsonResponseHandler<BaseBean<String>>() {
                            @Override
                            public void onSuccess(int statusCode, BaseBean<String> response) {
                                //上传成功后清空数据库
                                ordersDao.deleteAll();
                            }
                        });
                    }
                }
            }
        }, 10000, 10 * 60 * 1000);
    }

}