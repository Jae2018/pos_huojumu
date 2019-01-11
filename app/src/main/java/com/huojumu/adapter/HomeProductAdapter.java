package com.huojumu.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.Products;
import com.huojumu.utils.GlideApp;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeProductAdapter extends BaseQuickAdapter<Products.ProductsBean, BaseViewHolder> {

    public HomeProductAdapter(@Nullable List<Products.ProductsBean> data) {
        super(R.layout.item_home_product_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Products.ProductsBean item) {
        ImageView iv = helper.getView(R.id.iv_product_url);
        GlideApp.with(mContext).load(item.getImgs().get(0).getPath()).into(iv);
        helper.setText(R.id.tv_product_cut, String.format("¥ %s", item.getOrigionPrice()))
                .setText(R.id.tv_product_name, item.getProName())
                .setVisible(R.id.tv_for_sell_out, "1".equals(item.getIsSaled()));
    }

}
