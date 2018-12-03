package com.huojumu.main.dialogs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.main.activity.function.ChangePwdActivity;
import com.huojumu.main.activity.function.MaterialActivity;
import com.huojumu.main.activity.function.OrdersListActivity;
import com.huojumu.main.activity.function.PayBackActivity;
import com.huojumu.main.activity.function.SettingActivity;
import com.huojumu.main.activity.function.VipActivity;
import com.huojumu.main.activity.work.InventoryActivity;
import com.huojumu.utils.PrinterUtil;

import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/18
 * Description: 更多功能弹窗
 */
public class MoreFunctionDialog extends BaseDialog {

    private Intent intent = new Intent();

    public MoreFunctionDialog(@NonNull Context context) {
        super(context);
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
        intent.setClass(getContext(), PayBackActivity.class);
        getContext().startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.iv_more_fuc4)
    void Fuc2() {
        //系统设置
        intent.setClass(getContext(), SettingActivity.class);
        getContext().startActivity(intent);
        dismiss();
    }

//    @OnClick(R.id.iv_more_fuc2)
//    void Fuc3() {
//        //后台管理
//
//        Toast.makeText(getContext(), "暂未开放", Toast.LENGTH_LONG).show();
////        dismiss();
//    }
//
//    @OnClick(R.id.iv_more_fuc3)
//    void Fuc4() {
//        //估清限量
//        Toast.makeText(getContext(), "暂未开放", Toast.LENGTH_LONG).show();
////        dismiss();
//
//    }

    @OnClick(R.id.iv_more_fuc5)
    void Fuc5() {
        intent.setClass(getContext(), ChangePwdActivity.class);
        getContext().startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.iv_more_fuc6)
    void Fuc6() {
        PrinterUtil.OpenMoneyBox();
        dismiss();
    }

    @OnClick(R.id.iv_more_fuc7)
    void Fuc7() {
        // 会员信息
        intent.setClass(getContext(), VipActivity.class);
        getContext().startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.iv_more_fuc8)
    void Fuc8() {
        //账单查询
        intent.setClass(getContext(), OrdersListActivity.class);
        getContext().startActivity(intent);
        dismiss();
    }

//    @OnClick(R.id.iv_more_fuc9)
//    void Fuc9() {
//        //白盒子支付
//        intent.setClass(getContext(), PayByBoxActivity.class);
//        getContext().startActivity(intent);
//        dismiss();
//    }
//
//    @OnClick(R.id.iv_more_fuc10)
//    void Fuc10() {
//        // 原料统计
//        intent.setClass(getContext(), MaterialActivity.class);
//        intent.putExtra("title", "原料统计");
//        getContext().startActivity(intent);
//        dismiss();
//    }

    @OnClick(R.id.iv_more_fuc11)
    void Fuc11() {
        // 库存查询
        intent.setClass(getContext(), MaterialActivity.class);
        intent.putExtra("title", "库存查询");
        getContext().startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.iv_more_fuc12)
    void Fuc12() {
        // 盘点
        intent.setClass(getContext(), InventoryActivity.class);
        getContext().startActivity(intent);
        dismiss();
    }

}
