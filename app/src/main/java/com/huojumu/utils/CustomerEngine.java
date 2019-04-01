package com.huojumu.utils;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.huojumu.main.activity.dialog.DifferentDisplay;
import com.huojumu.model.Production;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/12/3
 * Description: qq：494669467，wx：s494669467
 */
public class CustomerEngine {

    // 获取设备上的屏幕
    private DifferentDisplay mCustomerDisplay;   //（继承Presentation）

    private static CustomerEngine instance;

    /**
     * 单例模式，创建的时候把界面绑定到第二个屏幕中
     *
     * @param context 这里需要传入getApplicationContext(),就能实现全局双屏异显
     */
    public static CustomerEngine getInstance(Context context) {
        if (instance == null) {
            instance = new CustomerEngine(context);
        }
        return instance;
    }

    private CustomerEngine(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager != null) {
            Display[] presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if (presentationDisplays.length > 0) {
                Display display = presentationDisplays[0];
                mCustomerDisplay = new DifferentDisplay(context, display);
                mCustomerDisplay.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                mCustomerDisplay.show();
            }
        }
    }

    public void refresh(List<Production> list) {
        if (mCustomerDisplay != null)
            mCustomerDisplay.refresh(list);
    }

    public void clear(){
        if (mCustomerDisplay != null)
            mCustomerDisplay.clear();
    }

    public void setPrice(double total, double cut) {
        if (mCustomerDisplay != null)mCustomerDisplay.setPrice(total, cut);
    }

    public ImageView getAliIV() {
        return mCustomerDisplay.getAliIV();
    }

    public ImageView getWxIV() {
        return mCustomerDisplay.getAliIV();
    }

    public ImageView getAdsImage(){
        return mCustomerDisplay.getAdsLinear();
    }

    public WebView getWebView(){
        return mCustomerDisplay.getWeb_order();
    }

}
