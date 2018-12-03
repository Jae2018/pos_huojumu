package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.OrdersList;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/30
 * Description: qq：494669467，wx：s494669467
 */
public class OrderListAdapter extends BaseQuickAdapter<OrdersList, BaseViewHolder> {

    public OrderListAdapter(@Nullable List<OrdersList> data) {
        super(R.layout.item_for_orders_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrdersList item) {
        helper.setText(R.id.tv_order_list_no, "编号：" + item.getOrderNo())
                .setText(R.id.tv_order_list_status, "状态：" + item.getStatus())
                .setText(R.id.tv_order_list_name, "商品：" + item.getProName());
    }
}
