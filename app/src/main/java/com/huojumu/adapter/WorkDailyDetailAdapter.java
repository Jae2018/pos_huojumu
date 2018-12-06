package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.data.OrderDetail;
import com.huojumu.R;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/12/6
 * Description: qq：494669467，wx：s494669467
 */
public class WorkDailyDetailAdapter extends BaseQuickAdapter<OrderDetail,BaseViewHolder> {

    public WorkDailyDetailAdapter(@Nullable List<OrderDetail> data) {
        super(R.layout.item_wor_daily_takeover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetail item) {
        helper.setText(R.id.tv_daily_list_item_no, String.format("产品名称：%s", item.getProName()))
                    .setText(R.id.tv_daily_list_item_time, String.format("单价：%s", item.getSell()))
                    .setText(R.id.tv_daily_list_item_money, String.format("数量：%.2s", item.getNumber()));
    }
}
