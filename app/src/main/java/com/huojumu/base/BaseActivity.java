package com.huojumu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.huojumu.utils.CustomerEngine;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import butterknife.ButterKnife;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected CustomerEngine engine;
    protected LoadingDialog ld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        ButterKnife.bind(this);

        engine = CustomerEngine.getInstance(getApplicationContext());
        initView();
        initData();

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

}
