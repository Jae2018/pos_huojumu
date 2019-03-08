package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.DailyInfo;

import java.util.List;
import java.util.Locale;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description: 交班、日结明细列表
 */
public class WorkDailyAdapter extends BaseQuickAdapter<DailyInfo.OrdersBean.RowsBean, BaseViewHolder> {

    public WorkDailyAdapter(@Nullable List<DailyInfo.OrdersBean.RowsBean> data) {
        super(R.layout.item_wor_daily_takeover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyInfo.OrdersBean.RowsBean item) {
        helper.setText(R.id.tv_daily_list_item_no, String.format("订单编号：C%s", item.getOrdNo().substring(item.getOrdNo().length() - 8)))
                .setText(R.id.tv_daily_list_item_time, String.format("生成时间：%s", item.getOrderTime()))
                .setText(R.id.tv_daily_list_item_pay, String.format("支付方式：%s", item.getPayType().equals("900") ? "现金支付" : "移动支付"))
                .setText(R.id.tv_daily_list_item_cut, String.format("优惠金额：%s 元", item.getOrgionTotalPrice() - item.getTotalPrice()))
                .setText(R.id.tv_daily_list_item_money, String.format(Locale.CHINA,"订单总价：%.2f 元", item.getTotalPrice()));
    }

}
