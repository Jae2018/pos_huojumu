package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.OrderBackBean;

import java.util.List;

/***
 * 可退单列表
 */
public class OrderEnableBackAdapter extends BaseQuickAdapter<OrderBackBean , BaseViewHolder> {

    public OrderEnableBackAdapter(@Nullable List<OrderBackBean> data) {
        super(R.layout.item_order_back_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBackBean item) {
        helper.setText(R.id.tv_order_back_no, String.format("C%s",item.getOrdNo().substring(item.getId().length() - 8)))
                .setText(R.id.tv_order_back_date, String.format("C%s",item.getCreateTime()));
    }
}
