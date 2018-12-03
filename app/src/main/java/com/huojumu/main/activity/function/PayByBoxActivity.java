package com.huojumu.main.activity.function;

import android.content.Intent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/11/6
 * Description:
 */
public class PayByBoxActivity extends BaseActivity {

    @BindView(R.id.wv_pay_box)
    WebView webView;

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_by_box;
    }

    @Override
    protected void initView() {
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_back)
    void back() {
        startActivity(new Intent(PayByBoxActivity.this, HomeActivity.class));
        finish();
    }

}
