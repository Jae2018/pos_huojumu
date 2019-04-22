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

    public static void I(Class c,String msg) {
        Log.i(c.getSimpleName(), "LogI: " + msg);
    }

    public static void D(Class c,String msg) {
        Log.d(c.getSimpleName(), "LogD: " + msg);
    }

    public static void E(String msg) {
        Log.e(getAutoJumpLogInfos()[0], getAutoJumpLogInfos()[1] + getAutoJumpLogInfos()[2]);
    }

    public static String[] getAutoJumpLogInfos() {
        String[] infos = new String[] { "", "", "" };
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 5) {
            Log.e("MyLogger", "Stack is too shallow!!!");
            return infos;
        } else {
            infos[0] = elements[4].getClassName().substring(
                    elements[4].getClassName().lastIndexOf(".") + 1);
            infos[1] = elements[4].getMethodName() + "()";
            infos[2] = " at (" + elements[4].getClassName() + ".java:"
                    + elements[4].getLineNumber() + ")";
            return infos;
        }
    }
}
