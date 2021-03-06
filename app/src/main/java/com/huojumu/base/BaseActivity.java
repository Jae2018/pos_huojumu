package com.huojumu.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.huojumu.utils.CustomerEngine;
import com.huojumu.wedgit.CustomProgressDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;

import butterknife.ButterKnife;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected CustomerEngine engine;
    protected CustomProgressDialog progressDialog;
    public static ArrayList<String> per = new ArrayList<>();
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH
    };
    private static final int REQUEST_CODE = 0x004;
    protected Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(setLayout());
        ButterKnife.bind(this);

        progressDialog = new CustomProgressDialog(this);

        engine = CustomerEngine.getInstance(getApplicationContext());
        initView();
        initData();
    }

    protected void checkPermission() {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                per.add(permission);
            }
        }
    }

    protected void requestPermission() {
        if (per.size() > 0) {
            String[] p = new String[per.size()];
            ActivityCompat.requestPermissions(this, per.toArray(p), REQUEST_CODE);
        }
    }

    // 设置布局
    protected abstract int setLayout();

    // 初始化组件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        disableStatusBar();
        super.onWindowFocusChanged(hasFocus);
    }

    public void disableStatusBar() {
        String N = "statusbar";
        try {
            Object service = getSystemService(N);
            @SuppressLint("PrivateApi") Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod("disable", int.class);
            //判断版本大小
            expand.invoke(service, 0x00010000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
