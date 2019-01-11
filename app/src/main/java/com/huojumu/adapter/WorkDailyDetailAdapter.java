package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.data.OrderInfoNo;
import com.huojumu.R;

import java.util.List;
import java.util.Locale;

/**
 * @author : Jie
 * Date: 2018/12/6
 * Description: qq：494669467，wx：s494669467
 */
public class WorkDailyDetailAdapter extends BaseQuickAdapter<OrderInfoNo,BaseViewHolder> {

    public WorkDailyDetailAdapter(@Nullable List<OrderInfoNo> data) {
        super(R.layout.item_wor_daily_takeover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfoNo item) {
        helper.setText(R.id.tv_daily_list_item_no, String.format("名称：%s", item.getProMateName()))
                    .setText(R.id.tv_daily_list_item_time, String.format(Locale.CHINA,"单价：%.2f", item.getSell()))
                    .setText(R.id.tv_daily_list_item_money, String.format(Locale.CHINA,"数量：%d", item.getNumber()));
    }
}
