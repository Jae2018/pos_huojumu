package com.huojumu;

import android.app.Application;
import android.content.Context;

import com.huojumu.utils.Constant;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SocketTool;
import com.huojumu.utils.SpUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.UUID;


/**
 * @author : Jie
 * Date: 2018/5/25
 * <p>
 * Description:
 */
public class MyApplication extends Application {

    private static Context mContext;
    protected static SocketTool socketTool;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashReport.initCrashReport(mContext, "551785ca95", true);
        SpUtil.Instance(this);
        SpUtil.save(Constant.UUID, UUID.randomUUID().toString());
        PrinterUtil.connectPrinter(mContext);
        socketTool = SocketTool.getInstance(this);
    }


    public static SocketTool getSocketTool() {
        return socketTool;
    }

    public static Context getContext(){
        return mContext;
    }

}
