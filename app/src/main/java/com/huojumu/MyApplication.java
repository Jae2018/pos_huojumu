package com.huojumu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.BitmapDrawable;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.huojumu.services.MyPosService;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SocketTool;
import com.huojumu.utils.SpUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuexiang.xaop.XAOP;

import org.greenrobot.greendao.database.Database;

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
    private static Bitmap line1, line2, line3, line4, logo, qrcode;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //bugly 测试版本时候用
        CrashReport.initCrashReport(mContext, "551785ca95", true);

        //初始化sp
        SpUtil.Instance(this);
        //产生唯一uuid
        SpUtil.save(Constant.UUID, UUID.randomUUID().toString());

        //初始化websocket
        initSocket();

        //小票所用图片，需提前加载。暂时不用
        line1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.line1)).getBitmap();
        line2 = ((BitmapDrawable) getResources().getDrawable(R.drawable.line2)).getBitmap();
        line3 = ((BitmapDrawable) getResources().getDrawable(R.drawable.line3)).getBitmap();
        line4 = ((BitmapDrawable) getResources().getDrawable(R.drawable.line4)).getBitmap();
        logo = ((BitmapDrawable) getResources().getDrawable(R.drawable.logo)).getBitmap();
        qrcode = ((BitmapDrawable) getResources().getDrawable(R.drawable.qr_code)).getBitmap();

        //初始化数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "pos-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        //初始化切面编程脚本
        XAOP.init(this);

        String p = "appid=" + getString(R.string.app_id) + "," + SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC;
        SpeechUtility.createUtility(MyApplication.this, p);

        startService(new Intent(this, MyPosService.class));

        SpUtil.save("firstRun", true);
    }

    public static void initSocket() {
        socketTool = SocketTool.getInstance();
        socketTool.init();
    }

    public static Bitmap getLine4() {
        return line4;
    }

    public static Bitmap getQrcode() {
        return qrcode;
    }

    public static Bitmap getLogo() {
        return logo;
    }

    public static Bitmap getLine1() {
        return line1;
    }

    public static Bitmap getLine2() {
        return line2;
    }

    public static Bitmap getLine3() {
        return line3;
    }

    public static SocketTool getSocketTool() {
        return socketTool;
    }

    public static Context getContext() {
        return mContext;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
