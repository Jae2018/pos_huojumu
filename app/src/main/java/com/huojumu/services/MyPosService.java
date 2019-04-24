package com.huojumu.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.huojumu.MyApplication;
import com.huojumu.model.NativeOrders;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyPosService extends Service {

    private List<NativeOrders> nativeOrdersList;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        nativeOrdersList = ((MyApplication) getApplication()).getDaoSession().getNativeOrdersDao().loadAll();
        if (nativeOrdersList != null && !nativeOrdersList.isEmpty()) {

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startLoop(){
        if (timer == null) {
            timer = new Timer();
        }
        //间隔10分钟轮询一次网络状态
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 10000, 10 * 60 * 1000);
    }

}
