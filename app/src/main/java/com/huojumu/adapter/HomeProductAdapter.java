package com.huojumu.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.Production;
import com.huojumu.model.ScaleBean;
import com.huojumu.utils.GlideApp;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeProductAdapter extends BaseQuickAdapter<Production, BaseViewHolder> {

    private List<ScaleBean> scaleBeanList;

    public HomeProductAdapter(@Nullable List<Production> data) {
        super(R.layout.item_home_product_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Production item) {
        ImageView iv = helper.getView(R.id.iv_product_url);
        if (!item.getImgs().isEmpty()) {
            GlideApp.with(mContext).load(item.getImgs().get(0).getPath()).placeholder(R.drawable.placeholder).into(iv);
        }
        helper.setText(R.id.tv_product_cut, String.format("¥ %s", (item.getIsBargain() != null) && (item.getIsBargain().equals("1")) ? item.getPrice() : item.getOrigionPrice()))
                .setText(R.id.tv_product_name, item.getProName());
        for (ScaleBean scaleBean : scaleBeanList)
            if (item.getScaleId() == scaleBean.getScaleId()) {
                helper.setText(R.id.tv_product_scale, scaleBean.getScaName());
            }
    }

    public void setScale(List<ScaleBean> scaleBeanList){
        this.scaleBeanList = scaleBeanList;
    }

}
