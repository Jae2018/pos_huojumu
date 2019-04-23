package com.huojumu.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 静默安装
 */
public class UpdateTool {

    public void excutesucmd(Context context, String filepath) {
        Process process;
        OutputStream out = null;
        InputStream in = null;
        try {
            // 请求root
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            // 调用安装
            out.write(("pm install -r " + filepath + "\n").getBytes());
            in = process.getInputStream();
            int len;
            byte[] bs = new byte[256];
            while (-1 != (len = in.read(bs))) {
                String state = new String(bs, 0, len);
                if (state.equals("success\n")) {
                    //安装成功后的操作

                    //静态注册自启动广播
                    Intent intent = new Intent();
                    //与清单文件的receiver的anction对应
                    intent.setAction("android.intent.action.PACKAGE_REPLACED");
                    //发送广播
                    context.sendBroadcast(intent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
