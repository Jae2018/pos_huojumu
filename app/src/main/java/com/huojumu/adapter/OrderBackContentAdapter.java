package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.OrderBackInfo;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description: qq：494669467，wx：s494669467
 */
public class OrderBackContentAdapter extends BaseQuickAdapter<OrderBackInfo.OrderdetailBean.ProsBean,BaseViewHolder> {

    public OrderBackContentAdapter(@Nullable List<OrderBackInfo.OrderdetailBean.ProsBean> data) {
        super(R.layout.item_for_order_back, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBackInfo.OrderdetailBean.ProsBean item) {
        helper.setText(R.id.tv_order_item_content, item.getProName() + " * " + item.getProCount());
    }
}
