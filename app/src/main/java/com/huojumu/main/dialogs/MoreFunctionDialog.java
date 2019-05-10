package com.huojumu.main.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.main.activity.function.InventoryActivity;
import com.huojumu.main.activity.function.OrdersListActivity;
import com.huojumu.main.activity.function.PayBackActivity;
import com.huojumu.main.activity.function.SettingActivity;
import com.huojumu.main.activity.function.VipActivity;
import com.huojumu.main.activity.work.DailyTakeOverActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.PrinterUtil;

import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/18
 * Description: 更多功能弹窗
 */
public class MoreFunctionDialog extends BaseDialog {

    private Intent intent = new Intent();
    private Activity activity;

    public MoreFunctionDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_home_more_function;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btn_close_dialog)
    void Close() {
        cancel();
    }

    @OnClick(R.id.iv_more_fuc1)
    void Fuc1() {
        //退单
        intent.setClass(activity, PayBackActivity.class);
        getContext().startActivity(intent);
        cancel();
    }

    @OnClick(R.id.iv_more_fuc4)
    void Fuc2() {
        //交班
        Intent intent = new Intent(activity, DailyTakeOverActivity.class);
        intent.putExtra("type", 1);
        activity.startActivityForResult(intent, Constant.WORK_BACK_OVER);
        cancel();
    }

    @OnClick(R.id.iv_more_fuc5)
    void Fuc5() {
        //日结
        Intent intent = new Intent(activity, DailyTakeOverActivity.class);
        intent.putExtra("type", 2);
        activity.startActivityForResult(intent, Constant.WORK_BACK_DAILY);
        cancel();
    }

    @OnClick(R.id.iv_more_fuc6)
    void Fuc6() {
        PrinterUtil.OpenMoneyBox();
        cancel();
    }

    @OnClick(R.id.iv_more_fuc7)
    void Fuc7() {
        // 会员信息
        intent.setClass(getContext(), VipActivity.class);
        activity.startActivity(intent);
        cancel();
    }

    @OnClick(R.id.iv_more_fuc8)
    void Fuc8() {
        //账单查询
        intent.setClass(getContext(), OrdersListActivity.class);
        activity.startActivity(intent);
        cancel();
    }

    @OnClick(R.id.iv_more_fuc11)
    void Fuc11() {
        // 设置
        intent.setClass(getContext(), SettingActivity.class);
        intent.putExtra("title", "设置");
        getContext().startActivity(intent);
        cancel();
    }

    @OnClick(R.id.iv_more_fuc12)
    void Fuc12() {
        // 盘点
        intent.setClass(getContext(), InventoryActivity.class);
        activity.startActivity(intent);
        cancel();
    }

}
