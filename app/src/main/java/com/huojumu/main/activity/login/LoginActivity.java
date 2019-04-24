package com.huojumu.main.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.down.DownProgressDialog;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
import com.huojumu.model.NetErrorHandler;
import com.huojumu.model.UpdateBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SpUtil;
import com.huojumu.utils.UpdateTool;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.qr_image)
    ImageView imageCode;
    @BindView(R.id.out_of_date)
    TextView out_of_date;

    private CountDownTimer countDownTimer;
    private DownProgressDialog downProgressDialog;
    private CertainDialog dialog;
    //重连socket消息flag
    private static final int RECONNECT_SOCKET = 2;
    //重连次数
    private static int RECONNECT_TIME = 1;
    private MyHandler mHandler;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        NetTool.updateApk(new GsonResponseHandler<UpdateBean>() {
            @Override
            public void onSuccess(int statusCode, UpdateBean response) {
                if (!UpdateTool.getLocalVersionName(LoginActivity.this).equals(response.getVersionNum())) {
                    MyOkHttp.mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downProgressDialog = new DownProgressDialog(LoginActivity.this);
                            downProgressDialog.setCancelable(false);
                            downProgressDialog.show();
                        }
                    }, 500);
                }
            }
        });
        mHandler = new MyHandler(this);
    }


    @Override
    protected void initData() {
        MyOkHttp.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCode();
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendSocket();
    }

    private void getCode() {
        //获取二维码
        NetTool.getLoginQRCode(SpUtil.getString(Constant.EQP_NO), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                if (!response.getData().isEmpty()) {
                    Bitmap b = QrUtil.createQRCode(LoginActivity.this, response.getData());
                    imageCode.setImageBitmap(b);
                    countDownTimer = new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            out_of_date.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            out_of_date.setVisibility(View.VISIBLE);
                            countDownTimer.cancel();
                        }
                    };
                    countDownTimer.start();
                }
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                ToastUtils.showLong(error_msg);
            }
        });
    }

    @OnClick(R.id.qr_image)
    void refreshCode() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        getCode();
    }

    @OnClick(R.id.iv_shutdown)
    void shutdown() {
        PowerUtil.shutdown();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(EventHandler eventHandler) {
        if (eventHandler.getType() == 3) {
            SpUtil.save("from_Login", true);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            if (dialog == null) {
                dialog = new CertainDialog(this);
            }
            dialog.show();
            dialog.setText("注意！", "您当前无法登陆，上一班次未正常交班\n请联系 "
                    + eventHandler.getUserName() + " 完成交班\n联系电话：" + eventHandler.getMobile());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (downProgressDialog != null) {
            downProgressDialog.cancel();
            downProgressDialog = null;
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<LoginActivity> mActivity;

        MyHandler(LoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
            if (msg.what == RECONNECT_SOCKET) {
                //尝试重连
                if (activity != null && !MyApplication.getSocketTool().isAlive()) {
                    sendEmptyMessageDelayed(RECONNECT_SOCKET, (10 * 1000) * RECONNECT_TIME);
                    activity.reconnectSocket();
                }
            }
        }
    }

    /**
     * 重启socket
     */
    private void reconnectSocket() {
        RECONNECT_TIME++;
        MyApplication.startSocket();
        sendSocket();
    }

    private void sendSocket() {
        //定时发送心跳
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MyApplication.getSocketTool().sendHeart();
                }
            }, 200, 6 * 1000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netState(NetErrorHandler netErrorHandler) {
        if (netErrorHandler.isConnected()) {
            //网络正常
            RECONNECT_TIME = 1;
        } else {
            //无法正常连接到服务器
            MyApplication.getSocketTool().stopSocket();
            //关闭心跳，不继续发送，轮询时间段重启websocket，首次延迟10秒
            if (timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
                if (RECONNECT_TIME == 1) {
                    mHandler.sendEmptyMessageDelayed(RECONNECT_SOCKET, 10000);
                }
            }
        }
    }
}
