package com.huojumu.utils;

import android.util.Log;

/**
 * @author : Jie
 * Date: 2018/9/21
 * Description:
 */
public class LogUtil {

    private static LogUtil INSTANCE;

    public static LogUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogUtil();
        }
        return INSTANCE;
    }

    public static void Logi(Class c,String msg) {
        Log.i(c.getSimpleName(), "LogI: " + msg);
    }

    public static void LogD(Class c,String msg) {
        Log.d(c.getSimpleName(), "LogD: " + msg);
    }

    public static void Loge(Class c,String msg) {
        Log.e(c.getSimpleName(), "LogE: " + msg);
    }
}
