package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.data.OrderSave;
import com.huojumu.R;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description: 交班、日结明细列表
 */
public class WorkDailyAdapter extends BaseQuickAdapter<OrderSave, BaseViewHolder> {


    public WorkDailyAdapter(@Nullable List<OrderSave> data) {
        super(R.layout.item_wor_daily_takeover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderSave item) {
        helper.setText(R.id.tv_daily_list_item_no, String.format("订单编号：%s", item.getProName()))
                .setText(R.id.tv_daily_list_item_time, String.format("生成时间：%s", item.getTime()))
                .setText(R.id.tv_daily_list_item_money, String.format("订单总价：%.2s", item.getPrice()));
    }

}
