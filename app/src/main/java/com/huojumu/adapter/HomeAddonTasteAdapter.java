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
 * Description: 口味列表
 */
public class HomeAddonTasteAdapter extends BaseQuickAdapter<Products.TastesBean, BaseViewHolder> {

    public HomeAddonTasteAdapter(@Nullable List<Products.TastesBean> data) {
        super(R.layout.item_home_addon_taste, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Products.TastesBean item) {
        helper.setText(R.id.tv_home_addon_taste, item.getTasteName()).setBackgroundColor(R.id.tv_home_addon_taste, item.isSelected() ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.btn_den));
    }

}
