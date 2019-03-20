package com.huojumu.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
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
//    private BaseActivity activity;
    private WebSocket webSocket;
    private static SocketTool INSTANCE;
    private Context context;
    private static Thread thread;

    public static SocketTool getInstance(Context context) {
        Request request = new Request.Builder()
                .url(Constant.SOCKET)
                .build();
        OkHttpClient client = new OkHttpClient();
        INSTANCE = new SocketTool(context);
        client.newWebSocket(request, INSTANCE);
        return INSTANCE;
    }

    private SocketTool(Context context) {
        this.context = context;
    }

    public void sendMsg(String s) {
        if (webSocket != null) {
            webSocket.send(s);
        }
    }

    public void sendHeart() {
        if (webSocket != null) {
            thread = new Thread() {
                public void run() {
                    while (true) {
                        webSocket.send("{\"task\": \"heartbeat\",\"machineCode\":\"" + SpUtil.getString(Constant.EQP_NO) + "\",\"shopID\":\"" + SpUtil.getInt(Constant.STORE_ID) + "\",\"eqpType\":\"3\"}");
                        try {
                            Thread.sleep(600 * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            new Throwable().printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
    }

    @Override
    public void onMessage(final WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.e(TAG, "onMessage: " + text);
        TaskBean taskBean = gson.fromJson(text, TaskBean.class);
        switch (taskBean.getTask()) {
            case Constant.BIND:
                //绑定设备回调
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
                        EventBus.getDefault().post(new EventHandler(Constant.LOGIN));
                    }

                    @Override
                    public void onFailure(int statusCode,String code, String error_msg) {
                        ToastUtils.showLong(error_msg);
                    }
                });
                break;
            case Constant.PAYCODE:
                //支付完成回调
                EventBus.getDefault().post(taskBean);
                break;
            case Constant.START:
                //扫码登录回调
                SpUtil.save(Constant.WORKER_NAME, taskBean.getData().getUserName());
                SpUtil.save(Constant.MY_TOKEN, "Bearer "+taskBean.getData().getToken());
                EventBus.getDefault().post(new EventHandler(Constant.HOME));
                break;
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
