package com.huojumu;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SocketTool;
import com.huojumu.utils.SpUtil;
import com.tencent.bugly.crashreport.CrashReport;

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
    private static Bitmap line1,line2,line3,logo,qrcode;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashReport.initCrashReport(mContext, "551785ca95", true);
        SpUtil.Instance(this);
        SpUtil.save(Constant.UUID, UUID.randomUUID().toString());
        socketTool = SocketTool.getInstance(this);

        line1 = ((BitmapDrawable)getResources().getDrawable(R.drawable.line1)).getBitmap();
        line2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.line2)).getBitmap();
        line3 = ((BitmapDrawable)getResources().getDrawable(R.drawable.line3)).getBitmap();
        logo = ((BitmapDrawable)getResources().getDrawable(R.drawable.logo)).getBitmap();
        qrcode = ((BitmapDrawable)getResources().getDrawable(R.drawable.qr_code)).getBitmap();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "pos-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
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

    public static Context getContext(){
        return mContext;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
