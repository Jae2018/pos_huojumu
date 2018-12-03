package com.huojumu.main.activity.function;

import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.utils.NetTool;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import butterknife.BindView;
import butterknife.OnClick;



public class ChangePwdActivity extends BaseActivity {

    @BindView(R.id.et_old_pass)
    EditText oldEt;
    @BindView(R.id.et_new_pass)
    EditText newEt;

    @Override
    protected int setLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_cancel)
    void onCancel() {
        startActivity(new Intent(ChangePwdActivity.this, HomeActivity.class));
        finish();
    }

    @OnClick(R.id.btn_ok)
    void commit() {
        String old = oldEt.getText().toString();
        String n = newEt.getText().toString();
        NetTool.changePWD(old, n, new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                Toast.makeText(ChangePwdActivity.this, "修改成功，请牢记新密码", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ChangePwdActivity.this, HomeActivity.class));
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

}
