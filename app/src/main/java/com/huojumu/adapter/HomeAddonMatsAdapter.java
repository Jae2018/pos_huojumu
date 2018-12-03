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
 * Description:
 */
public class HomeAddonMatsAdapter extends BaseQuickAdapter<Products.ProductsBean.Mats, BaseViewHolder> {

    public HomeAddonMatsAdapter(@Nullable List<Products.ProductsBean.Mats> data) {
        super(R.layout.item_home_addon_taste, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Products.ProductsBean.Mats item) {
        helper.setText(R.id.tv_home_addon_taste, item.getMatName());
    }
}
