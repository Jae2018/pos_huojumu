package com.huojumu.utils;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.StoreInfo;
import com.huojumu.model.TaskBean;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketTool extends WebSocketListener {

    private String TAG = SocketTool.class.getSimpleName();
    private String sendStr;
    private Gson gson = new Gson();
    private BaseActivity activity;

    public SocketTool(BaseActivity activity, String sendStr) {
        this.activity = activity;
        this.sendStr = sendStr;
    }

    public void run() {
        Request request = new Request.Builder()
                .url(Constant.SOCKET)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        Log.e(TAG, sendStr);
        if (!sendStr.isEmpty()) {
            webSocket.send(sendStr);
        }
    }

    @Override
    public void onMessage(final WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.d(TAG, "onMessage:   " + text);

        TaskBean taskBean = gson.fromJson(text, TaskBean.class);
        if (taskBean.getTask().equals("machinebind")) {
            SpUtil.save(Constant.HAS_BAND, true);
            SpUtil.save(Constant.EQP_NO, taskBean.getData().getEqpNo());
            NetTool.getMachineInfo(taskBean.getData().getEqpNo(), new GsonResponseHandler<BaseBean<StoreInfo>>() {
                @Override
                public void onSuccess(int statusCode, BaseBean<StoreInfo> response) {
                    Log.e("Login", "onSuccess: " + response.getData());
                    if (response.getData() != null) {
                        SpUtil.save(Constant.STORE_ID, response.getData().getShop() == null ?
                                response.getData().getShop().getId() : 1);
                        SpUtil.save(Constant.STORE_NAME, response.getData().getShop() == null ?
                                "本地测试店铺" : response.getData().getShop().getShopName());
                        SpUtil.save(Constant.STORE_ADDRESS, response.getData().getShop() == null ?
                                "本地测试店铺地址" : response.getData().getShop().getAddr());
                        SpUtil.save(Constant.STORE_TEL, response.getData().getShop() == null ?
                                "本地测试店铺电话" : response.getData().getShop().getMobile());
                        SpUtil.save(Constant.PINPAI_ID, response.getData().getEnterPrise() == null ?
                                response.getData().getEnterPrise().getId() : 1);
                        SpUtil.save(Constant.ENT_ID, response.getData().getParentEnterPrise() == null ?
                                response.getData().getParentEnterPrise().getId() : 1);
                    }

                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                }

                @Override
                public void onFailure(int statusCode, String error_msg) {
                    Log.e("Login", "onFailure: " + error_msg);
                }
            });
        } else if (taskBean.getTask().equals("pay")) {
            activity.sendMsg(taskBean.getCode());
        }

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        Log.d(TAG, "bytes:   " + bytes);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        Log.d(TAG, "reason:   " + reason + "      code:  " + code);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        Log.d(TAG, "response:   " + response + "      t:  " + t);
    }
}
