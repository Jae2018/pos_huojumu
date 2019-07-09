package com.huojumu.main.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.down.DownProgressDialog;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
import com.huojumu.model.UpdateBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SingleClick;
import com.huojumu.utils.SpUtil;
import com.huojumu.utils.UpdateTool;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_back_bind)
    void backBind() {
        startActivity(new Intent(this, ActiveActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendSocket();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getCode();
                checkVersion();
            }
        }, 1000);

    }

    private void checkVersion() {
        progressDialog.show();
        NetTool.updateApk(new GsonResponseHandler<UpdateBean>() {
            @Override
            public void onSuccess(int statusCode, UpdateBean response) {
                if (!UpdateTool.getLocalVersionName(LoginActivity.this).equals(response.getVersionNum())) {
                    MyOkHttp.mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downProgressDialog = new DownProgressDialog(LoginActivity.this);
                            downProgressDialog.show();
                        }
                    }, 100);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
            }
        });
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
                ToastUtils.showLong("网络错误");
            }
        });
    }

    @SingleClick(1500)
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
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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

    private void sendSocket() {
        //定时发送心跳
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MyApplication.getSocketTool().sendHeart();
                Log.e("login", "sendHeart: ");
            }
        }, 200, 30 * 1000);
    }

}
