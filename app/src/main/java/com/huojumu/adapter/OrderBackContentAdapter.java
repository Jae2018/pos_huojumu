package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.OrderDetails;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/29
 * Description: 退单详情
 */
public class OrderBackContentAdapter extends BaseQuickAdapter<OrderDetails.OrderdetailBean.ProsBean,BaseViewHolder> {

    public OrderBackContentAdapter(@Nullable List<OrderDetails.OrderdetailBean.ProsBean> data) {
        super(R.layout.item_for_order_back, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetails.OrderdetailBean.ProsBean item) {
        String mats = "";
        for (OrderDetails.OrderdetailBean.ProsBean.MatsBean matsBean : item.getMats()) {
            mats += "-" + matsBean.getMatName() + " ￥" + matsBean.getIngredientPrice() + "\n";
        }

        helper.setText(R.id.tv_order_item_content, item.getProName() + " * " + item.getProCount() + "  " + item.getTastes().get(0).getTasteName() + "  " + item.getPrice())
                .setText(R.id.tv_order_item_adds, mats);
    }
}
