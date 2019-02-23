package com.huojumu.main.activity.login;

import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.qr_image)
    ImageView imageCode;
    @BindView(R.id.out_of_date)
    TextView out_of_date;

    private CountDownTimer countDownTimer;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getCode();
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

}
