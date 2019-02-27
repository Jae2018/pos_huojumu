package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.OrderDetailEntityListBean;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description: 退单详情
 */
public class OrderBackContentAdapter extends BaseQuickAdapter<OrderDetailEntityListBean,BaseViewHolder> {

    public OrderBackContentAdapter(@Nullable List<OrderDetailEntityListBean> data) {
        super(R.layout.item_for_order_back, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailEntityListBean item) {
        helper.setText(R.id.tv_order_item_content, item.getProName() + " * " + item.getProCount());
    }
}
