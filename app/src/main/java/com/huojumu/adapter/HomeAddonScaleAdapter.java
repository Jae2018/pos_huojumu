package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.Products;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/17
 * Description: 杯子规格
 */
public class HomeAddonScaleAdapter extends BaseQuickAdapter<Products.ScalesBean, BaseViewHolder> {

    public HomeAddonScaleAdapter(@Nullable List<Products.ScalesBean> data) {
        super(R.layout.item_home_addon_taste, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Products.ScalesBean item) {
        helper.setText(R.id.tv_home_addon_taste, item.getScaName())
                .setChecked(R.id.tv_home_addon_taste, item.isSelected());
    }
}
