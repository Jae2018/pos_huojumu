package com.huojumu.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
import com.huojumu.model.OrderBack;
import com.huojumu.model.StoreInfo;
import com.huojumu.model.TaskBean;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketTool extends WebSocketListener {

    private String TAG = SocketTool.class.getSimpleName();
    private Gson gson = new Gson();
    private BaseActivity activity;
    private WebSocket webSocket;
    private static SocketTool INSTANCE;
    private Context context;

    public static SocketTool getInstance(Context context) {
        Request request = new Request.Builder()
                .url(Constant.SOCKET)
                .build();
        OkHttpClient client = new OkHttpClient();
        INSTANCE = new SocketTool(context);
        client.newWebSocket(request, INSTANCE);
        Log.e("SocketTool", "getInstance: ");
        return INSTANCE;
    }

    private SocketTool(Context context) {
        this.context = context;
    }

    public void sendMsg(String s) {
        if (webSocket != null) {
            Log.e(TAG, "sendMsg: " + s);
            webSocket.send(s);
        }
    }

    public void sendHeart() {
        if (webSocket != null) {
            new Thread() {
                public void run() {
                    while (true) {
                        Log.e(TAG, "sendHeart: ");
                        webSocket.send("{\"task\": \"heartbeat\",\"machineCode\":\"" + SpUtil.getString(Constant.EQP_NO) + "\",\"shopID\":\"" + SpUtil.getInt(Constant.STORE_ID) + "\",\"eqpType\":\"3\"}");
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "run: error");
                        }
                    }
                }
            }.start();
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
//        if (!sendStr.isEmpty()) {
//            webSocket.send(sendStr);
//        }
    }

    @Override
    public void onMessage(final WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.e(TAG, "onMessage: " + text);
        TaskBean taskBean = gson.fromJson(text, TaskBean.class);
        if (taskBean.getTask().equals("machinebind")) {
            SpUtil.save(Constant.HAS_BAND, true);
            SpUtil.save(Constant.EQP_NO, taskBean.getData().getEqpNo());
            NetTool.getMachineInfo(taskBean.getData().getEqpNo(), new GsonResponseHandler<BaseBean<StoreInfo>>() {
                @Override
                public void onSuccess(int statusCode, BaseBean<StoreInfo> response) {
                    if (response.getData() != null) {
                        SpUtil.save(Constant.STORE_ID, response.getData().getShop().getId());
                        SpUtil.save(Constant.STORE_NAME, response.getData().getShop().getShopName());
                        SpUtil.save(Constant.STORE_ADDRESS, response.getData().getShop().getAddr());
                        SpUtil.save(Constant.STORE_TEL, response.getData().getShop().getMobile());
                        SpUtil.save(Constant.ENT_ID, response.getData().getEnterPrise().getId());
                        SpUtil.save(Constant.PINPAI_ID, response.getData().getParentEnterPrise().getId());
                        SpUtil.save(Constant.ENT_NAME, response.getData().getParentEnterPrise().getEntName());
                        SpUtil.save(Constant.ENT_DIS, response.getData().getParentEnterPrise().getDiscountsType());
                    }
//                    context.startActivity(new Intent(context, LoginActivity.class));
                    EventBus.getDefault().post(new EventHandler(Constant.LOGIN));
//                    activity.startActivity(new Intent(activity, LoginActivity.class));
//                    activity.finish();
                }

                @Override
                public void onFailure(int statusCode, String error_msg) {
                    Log.e("Login", "onFailure: " + error_msg);
                }
            });
        } else if (taskBean.getTask().equals("pay")) {
            Log.e(TAG, "onMessage: ");
            //支付完成回调
            EventBus.getDefault().post(new EventHandler(Constant.PAY));
        } else if (taskBean.getTask().equals("start")) {
//            EventBus.getDefault().post(new EventHandler(Constant.LOGIN));
            SpUtil.save(Constant.WORKER_NAME, taskBean.getData().getUserName());
            Log.e(TAG, "token: "+taskBean.getData().getToken());
            SpUtil.save(Constant.MY_TOKEN, "Bearer "+taskBean.getData().getToken());
            activity.startActivity(new Intent(activity, HomeActivity.class));
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
