package com.huojumu.main.activity;


import android.content.Intent;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.login.ActiveActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Intent intent = new Intent();
        if (SpUtil.getBoolean(Constant.HAS_BAND)) {
            intent.setClass(MainActivity.this, LoginActivity.class);
        } else {
            intent.setClass(MainActivity.this, ActiveActivity.class);
        }
        startActivity(intent);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {

    }
}
