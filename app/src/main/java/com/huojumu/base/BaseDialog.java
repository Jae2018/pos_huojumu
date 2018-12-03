package com.huojumu.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huojumu.R;

import butterknife.ButterKnife;

/**
 * @author : Jie
 * Date: 2018/9/29
 * Description:
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.customDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setLayout() != -1) {
            setContentView(setLayout());
            ButterKnife.bind(this);
            initView();
        }
    }

    public abstract int setLayout();

    public abstract void initView();

}
