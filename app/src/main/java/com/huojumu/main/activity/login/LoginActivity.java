package com.huojumu.main.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PowerUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.ed_account)
    EditText accountED;
    @BindView(R.id.ed_pwd)
    EditText pwdED;
    @BindView(R.id.image_code)
    ImageView imageCode;
    @BindView(R.id.ed_code)
    EditText edCode;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        edCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                    //do something;
                    Login();
                    return true;
                }
//                else if (event.getAction() == KeyEvent.ACTION_UP) {
//                    //扫描到的数据
//                    String s = v.getText().toString().trim();
//                    //拿到数据后做其他操作
//                    return true;
//                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        getCode();
    }

    private void getCode() {
        //获取验证码
        NetTool.getCode(SpUtil.getString(Constant.UUID), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                byte[] bytes = Base64.decode(response.getData().getBytes(), Base64.DEFAULT);
                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageCode.setImageBitmap(b);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.login_btn)
    void Login() {
        String name = accountED.getText().toString();
        String pwd = pwdED.getText().toString();
        String code = edCode.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_LONG).show();
            return;
        }
        if (code.isEmpty()) {
            Toast.makeText(this, "请输入验证码！", Toast.LENGTH_LONG).show();
            return;
        }
        NetTool.login(name, pwd, code, SpUtil.getString(Constant.UUID), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                if (response.getData().isEmpty()) {
                    ToastUtils.showLong(response.getMsg());
                    getCode();
                } else {
                    SpUtil.save(Constant.MY_TOKEN, "Bearer " + response.getData());
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.image_code)
    void refreshCode() {
        getCode();
    }

    @OnClick(R.id.iv_shutdown)
    void shutdown() {
        PowerUtil.shutdown();
    }
}
