package com.huojumu.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.PayMenuBean;

import java.util.List;

public class MenuRecyclerAdapter extends BaseQuickAdapter<PayMenuBean, BaseViewHolder> {

    public MenuRecyclerAdapter(@Nullable List<PayMenuBean> data) {
        super(R.layout.item_payment_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayMenuBean item) {
        helper.setText(R.id.tv_menu_recycler_item, item.getName());
        helper.getConvertView().setBackgroundColor(item.isSelected() ? Color.parseColor("#5e5d66") : Color.WHITE);
        helper.setTextColor(R.id.tv_menu_recycler_item, item.isSelected() ? Color.WHITE : Color.BLACK);
    }
}
