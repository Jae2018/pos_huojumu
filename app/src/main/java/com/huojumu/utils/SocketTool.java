package com.huojumu.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
import com.huojumu.model.H5TaskBean;
import com.huojumu.model.NetErrorHandler;
import com.huojumu.model.StoreInfo;
import com.huojumu.model.TaskBean;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketTool extends WebSocketListener {

    private String TAG = SocketTool.class.getSimpleName();
    private Gson gson = new Gson();
    private WebSocket webSocket;
    private static OkHttpClient client;
    private static SocketTool INSTANCE;
    private static Request request;
    private volatile boolean alive;

    public static SocketTool getInstance() {
        if (INSTANCE == null) {
            synchronized (SocketTool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SocketTool();
                }
            }
        }
        return INSTANCE;
    }

    public void init() {
        request = new Request.Builder()
                .url(Constant.SOCKET)
                .build();
        client = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();
        client.newWebSocket(request, INSTANCE);
    }

    public void sendMsg(String s) {
        if (webSocket != null) {
            webSocket.send(s);
        }
    }

    public void sendHeart() {
        if (webSocket != null) {
            Log.e(TAG, "sendHeart hashCode: " + webSocket.hashCode());
            webSocket.send("{\"task\": \"heartbeat\",\"machineCode\":\"" + SpUtil.getString(Constant.EQP_NO) + "\",\"shopID\":\"" + SpUtil.getInt(Constant.STORE_ID) + "\",\"eqpType\":\"3\"}");
        }
    }

    public void stopSocket() {
        webSocket = null;
        request = null;
        client = null;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void onMessage(final WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.e(TAG, "onMessage: " + text);
        EventBus.getDefault().post(new NetErrorHandler(true));

        alive = true;
        if (text.contains(Constant.PAYCODE)) {
            //来自小程序端
            H5TaskBean taskBean = gson.fromJson(text, H5TaskBean.class);
            EventBus.getDefault().post(taskBean);
        } else {
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
                                try {
                                    SpUtil.save(Constant.STORE_ID, response.getData().getShop().getId());
                                    SpUtil.save(Constant.STORE_NAME, response.getData().getShop().getShopName());
                                    SpUtil.save(Constant.STORE_ADDRESS, response.getData().getShop().getAddr());
                                    SpUtil.save(Constant.STORE_TEL, response.getData().getShop().getMobile());
                                    SpUtil.save(Constant.ENT_ID, response.getData().getEnterPrise().getId());
                                    SpUtil.save(Constant.PINPAI_ID, response.getData().getParentEnterPrise().getId());
                                    SpUtil.save(Constant.ENT_NAME, response.getData().getParentEnterPrise().getEntName());
                                    SpUtil.save(Constant.ENT_DIS, response.getData().getParentEnterPrise().getDiscountsType());
                                    SpUtil.save(Constant.HTML, response.getData().getParentEnterPrise().getReceiptTemplate());
                                } catch (Exception e) {
                                    Log.d(TAG, "onSuccess: " + e.getLocalizedMessage());
                                }
                            }
                            EventBus.getDefault().post(new EventHandler(Constant.LOGIN));
                        }

                        @Override
                        public void onFailure(int statusCode, String code, String error_msg) {
                            ToastUtils.showLong(error_msg);
                        }
                    });
                    break;
                case Constant.START:
                    //扫码登录回调
                    if (taskBean.getCode().equals("0")) {
                        SpUtil.save(Constant.WORKER_NAME, taskBean.getData().getUserName());
                        SpUtil.save(Constant.MY_TOKEN, "Bearer " + taskBean.getData().getToken());
                        EventBus.getDefault().post(new EventHandler(Constant.HOME));
                    } else {
                        EventBus.getDefault().post(new EventHandler(Constant.LOGIN_FAILED, taskBean.getData().getMobile(), taskBean.getData().getUserName()));
                    }
                    break;
            }
        }

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        alive = false;
        EventBus.getDefault().post(new NetErrorHandler(false));
    }

    public boolean isAlive() {
        return alive;
    }
}
