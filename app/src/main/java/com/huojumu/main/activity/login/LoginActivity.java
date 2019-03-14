package com.huojumu.main.activity.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.EventHandler;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.qr_image)
    ImageView imageCode;
    @BindView(R.id.out_of_date)
    TextView out_of_date;

    private CountDownTimer countDownTimer;

    ArrayList<String> per = new ArrayList<>();
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH
    };
    private static final int REQUEST_CODE = 0x004;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        checkPermission();
        requestPermission();
    }

    @Override
    protected void initData() {
        MyApplication.getSocketTool().sendMsg("{\"task\": \"heartbeat\",\"machineCode\":\"" + SpUtil.getString(Constant.EQP_NO) + "\",\"shopID\":\"" + SpUtil.getInt(Constant.STORE_ID) + "\",\"eqpType\":\"3\"}");

        getCode();
        if (!SpUtil.getBoolean("Daily_success")) {
            //非正常日结情况
            daily();
        }
    }



    private void checkPermission() {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                per.add(permission);
            }
        }
    }

    private void requestPermission() {
        if (per.size() > 0) {
            String[] p = new String[per.size()];
            ActivityCompat.requestPermissions(this, per.toArray(p), REQUEST_CODE);
        }
    }

    private void getCode() {
        //获取二维码
        NetTool.getLoginQRCode(SpUtil.getString(Constant.EQP_NO), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
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

            @Override
            public void onFailure(int statusCode, String error_msg) {

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
    public void onLogin(EventHandler eventHandler){
        Log.e("Login", "Home: ");
        if (eventHandler.getType() == 3) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void daily() {
        NetTool.settlement(System.currentTimeMillis(), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
