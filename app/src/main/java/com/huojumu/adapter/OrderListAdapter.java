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
public class OrderListAdapter extends BaseQuickAdapter<OrdersList.RowsBean, BaseViewHolder> {

    public OrderListAdapter(@Nullable List<OrdersList.RowsBean> data) {
        super(R.layout.item_for_orders_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrdersList.RowsBean item) {
        helper.setText(R.id.tv_order_list_no, "订单号：C" + item.getOrdNo().substring(item.getOrdNo().length() - 8))
                .setText(R.id.tv_order_list_price, String.format("订单金额：%s 元", item.getTotalPrice()))
                .setText(R.id.tv_order_list_time, String.format("订单时间：%s", item.getCreateTime()))
                .setText(R.id.tv_order_list_status, "订单状态：" + (item.getStatus().equals("2") ? "未支付" : item.getStatus().equals("5") ? "已完成" : item.getStatus().equals("6") ? "已退单" : "等待中"))
                .setText(R.id.tv_order_list_type, String.format("支付方式：%s",  item.getPayType()!=null?item.getPayType().equals("010") ? "微信支付" : item.getPayType().equals("020") ? "支付宝支付" : "现金支付":"现金支付"));
    }
}
