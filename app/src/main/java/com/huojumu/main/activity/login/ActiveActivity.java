package com.huojumu.main.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.EventHandler;
import com.huojumu.utils.Constant;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


public class ActiveActivity extends BaseActivity {

    @BindView(R.id.qr_img)
    ImageView qr_img;

    private String uuid;

    @Override
    protected int setLayout() {
        return R.layout.activity_active;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        checkPermission();
        requestPermission();

        //生成设备编码二维码
        if (SpUtil.getString(Constant.EQP_NO) == null || SpUtil.getString(Constant.EQP_NO).isEmpty()) {
            uuid = SpUtil.getString(Constant.UUID);
        } else {
            uuid = SpUtil.getString(Constant.EQP_NO);
        }
        Bitmap b = QrUtil.createQRCode(ActiveActivity.this, uuid);
        qr_img.setImageBitmap(b);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MyApplication.getSocketTool().sendHeart();
                    Log.e("bind", "sendHeart: ");
                }
            }, 10, 60 * 1000);
        }
        if (uuid != null && !uuid.isEmpty()) {
            MyApplication.getSocketTool().sendMsg(String.format(Constant.BAND, uuid));
        }
    }

    @OnClick(R.id.iv_shutdown)
    void shutdown() {
        PowerUtil.shutdown();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(EventHandler eventHandler) {
        if (eventHandler.getType() == 2) {
            startActivity(new Intent(ActiveActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
        timer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
