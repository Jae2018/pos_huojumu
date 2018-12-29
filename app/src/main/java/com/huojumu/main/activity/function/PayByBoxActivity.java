package com.huojumu.main.activity.function;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;


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

    String loadUrl = "https://e.meituan.com/";

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_by_box;
    }

    @Override
    protected void initView() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        webView.loadUrl(loadUrl);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
