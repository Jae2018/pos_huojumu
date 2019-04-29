package com.tsy.sdk.myokhttp.response;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.util.LogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Gson类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class GsonResponseHandler<T> implements IResponseHandler {

    private Type mType;
    private Gson gson = new Gson();

    public GsonResponseHandler() {
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    private Type getType() {
        return mType;
    }

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr ;

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        final String finalResponseBodyStr = responseBodyStr;

        try {
            final T gsonResponse = gson.fromJson(finalResponseBodyStr, getType());
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(response.code(), gsonResponse);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "2","反序列化错误");
                }
            });
        }
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    @Override
    public void onFailure(int statusCode, String code, String error_msg) {

    }
}
