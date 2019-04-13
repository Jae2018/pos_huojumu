package com.huojumu;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

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
    private static Bitmap line1,line2,line3,logo,qrcode;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashReport.initCrashReport(mContext, "551785ca95", true);
        SpUtil.Instance(this);
        SpUtil.save(Constant.UUID, UUID.randomUUID().toString());
        PrinterUtil.connectPrinter(mContext);
        socketTool = SocketTool.getInstance(this);

        line1 = drawable2Bitmap(getResources().getDrawable(R.drawable.line11));
        line2 = drawable2Bitmap(getResources().getDrawable(R.drawable.line2));
        line3 = drawable2Bitmap(getResources().getDrawable(R.drawable.line3));
        logo = drawable2Bitmap(getResources().getDrawable(R.drawable.logo));
        qrcode = drawable2Bitmap(getResources().getDrawable(R.drawable.qr_code));
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

    private static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }



    public static SocketTool getSocketTool() {
        return socketTool;
    }

    public static Context getContext(){
        return mContext;
    }

}
