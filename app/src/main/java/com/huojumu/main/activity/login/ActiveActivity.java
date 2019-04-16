package com.huojumu.main.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.MainActivity;
import com.huojumu.model.EventHandler;
import com.huojumu.utils.Constant;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.QrUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class ActiveActivity extends BaseActivity {

    @BindView(R.id.qr_img)
    ImageView qr_img;

    String uuid;

    @Override
    protected int setLayout() {
        return R.layout.activity_active;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        checkPermission();
        requestPermission();

        if (SpUtil.getBoolean(Constant.HAS_BAND)) {
            Log.e("MainActivity", "initView: 1" );
            MyOkHttp.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    intent.setClass();
                    startActivity(new Intent(ActiveActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        } else {
            //生成设备编码二维码
            if (SpUtil.getString(Constant.UUID).isEmpty()) {
                uuid = UUID.randomUUID().toString();
                SpUtil.save(Constant.UUID, uuid);
            } else {
                uuid = SpUtil.getString(Constant.UUID);
            }
            Bitmap b = QrUtil.createQRCode(ActiveActivity.this, uuid);
            qr_img.setImageBitmap(b);
        }

    }

    @Override
    protected void initData() {

   }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getSocketTool().sendHeart();
        MyApplication.getSocketTool().sendMsg(String.format(Constant.BAND, uuid));
    }

    @OnClick(R.id.iv_shutdown)
    void shutdown() {
        PowerUtil.shutdown();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(EventHandler eventHandler){
        if (eventHandler.getType() == 2) {
            startActivity(new Intent(ActiveActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
