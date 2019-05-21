package com.huojumu.main.activity;


import android.content.Intent;
import android.os.Handler;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.login.ActiveActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;

public class MainActivity extends BaseActivity {

    Intent intent = new Intent();
    Handler handler = new Handler();

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        if (SpUtil.getBoolean(Constant.HAS_BAND)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    intent.setClass(MainActivity.this, ActiveActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {

    }
}
