package com.huojumu.main.activity;


import android.content.Intent;
import android.util.Log;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.login.ActiveActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;

public class MainActivity extends BaseActivity {

    Intent intent = new Intent();

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        Log.e("MainActivity", "initView: " );
        if (SpUtil.getBoolean(Constant.HAS_BAND)) {
            Log.e("MainActivity", "initView: 1" );
            MyOkHttp.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else {
            Log.e("MainActivity", "initView: 2" );
            MyOkHttp.mHandler.postDelayed(new Runnable() {
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
