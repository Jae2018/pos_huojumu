package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.Products;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeSelectedAdapter extends BaseItemDraggableAdapter<Products.ProductsBean, BaseViewHolder> {

    public HomeSelectedAdapter(@Nullable List<Products.ProductsBean> data) {
        super(R.layout.item_home_selected_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Products.ProductsBean item) {
        helper.setText(R.id.tv_home_selected_name, item.getProName())
                .setText(R.id.tv_home_selected_addon, "备注：" + item.getAddon())
                .setText(R.id.tv_home_selected_number, item.getNumber() + "份")
                .setText(R.id.tv_home_selected_taste, "口味：" + item.getTasteStr())
                .setText(R.id.tv_home_selected_cost, item.getNumber() + "*" + String.format("¥ %s", (item.getIsBargain() != null) && (item.getIsBargain().equals("1")) ? item.getPrice() : item.getOrigionPrice()) + "元");
    }
}
