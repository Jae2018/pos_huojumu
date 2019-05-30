package com.huojumu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author : Jie
 * Date: 2018/9/20
 * Description:
 */
public class SpUtil {

    private static SharedPreferences sp;

    public static void Instance(Context context) {
        sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
    }

    public static void save(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static void save(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key){
        return sp.getFloat(key, 0);
    }

    public static void save(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static void save(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static String getString(String key){
        return sp.getString(key, Constant.DEFAULT_STR);
    }

    public static int getInt(String key){
        return sp.getInt(key, Constant.DEFAULT_INT);
    }

    public static int getInt2(String key){
        return sp.getInt(key, 0);
    }

    public static boolean getBoolean(String key){
        return sp.getBoolean(key, false);
    }

}
