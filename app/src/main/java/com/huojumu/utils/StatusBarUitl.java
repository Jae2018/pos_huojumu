package com.huojumu.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description: 沉浸式状态栏工具类
 */
public class StatusBarUitl {

    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    // 重置状态栏。即把状态栏颜色恢复为app默认的主题色
//    public static void resetStatusBar(Activity activity) {
//        setStatusBarColor(activity, activity.getResources().getColor(R.color.colorPrimary));
//    }

    // 设置状态栏的背景色。对于Android4.4和Android5.0以上版本要区分处理
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
            // 底部导航栏颜色也可以由系统设置
            //activity.getWindow().setNavigationBarColor(color);
        }
        if (color == Color.TRANSPARENT) { // 透明背景表示要悬浮状态栏
            removeMarginTop(activity);
        } else { // 其它背景表示要恢复状态栏
            addMarginTop(activity);
        }
    }

    // 添加顶部间隔，留出状态栏的位置
    private static void addMarginTop(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View child = contentView.getChildAt(0);
        if (!TAG_MARGIN_ADDED.equals(child.getTag())) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) child.getLayoutParams();
            // 添加的间隔大小就是状态栏的高度
            params.topMargin += getStatusBarHeight(activity);
            child.setLayoutParams(params);
            child.setTag(TAG_MARGIN_ADDED);
        }
    }

    private static int getStatusBarHeight(Activity activity) {
        // 获得状态栏的高度
        int resourceId = activity.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    // 移除顶部间隔，霸占状态栏的位置
    private static void removeMarginTop(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View child = contentView.getChildAt(0);
        if (TAG_MARGIN_ADDED.equals(child.getTag())) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) child.getLayoutParams();
            // 移除的间隔大小就是状态栏的高度
            params.topMargin -= getStatusBarHeight(activity);
            child.setLayoutParams(params);
            child.setTag(null);
        }
    }

    // 对于Android4.4，系统没有提供设置状态栏颜色的方法，只能手工搞个假冒的状态栏来占坑
//    private static void setKitKatStatusBarColor(Activity activity, int statusBarColor) {
//        Window window = activity.getWindow();
//        ViewGroup decorView = (ViewGroup) window.getDecorView();
//        // 先移除已有的冒牌状态栏
//        View fakeView = decorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
//        if (fakeView != null) {
//            decorView.removeView(fakeView);
//        }
//        // 再添加新来的冒牌状态栏
//        View statusBarView = new View(activity);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
//        params.gravity = Gravity.TOP;
//        statusBarView.setLayoutParams(params);
//        statusBarView.setBackgroundColor(statusBarColor);
//        statusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
//        decorView.addView(statusBarView);
//    }
}
