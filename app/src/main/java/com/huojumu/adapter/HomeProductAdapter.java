package com.huojumu.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.ActivesBean;
import com.huojumu.model.Production;
import com.huojumu.utils.GlideApp;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeProductAdapter extends BaseQuickAdapter<Production, BaseViewHolder> {


    public HomeProductAdapter(@Nullable List<Production> data) {
        super(R.layout.item_home_product_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Production item) {
        ImageView iv = helper.getView(R.id.iv_product_url);
        if (!item.getImgs().isEmpty()) {
            GlideApp.with(mContext).load(item.getImgs().get(0).getPath()).placeholder(R.drawable.placeholder).into(iv);
        }
        String activeStr = "";
        for (ActivesBean activesBean : item.getActivesBeanList()) {
            if (activesBean.isOpen()) {
                switch (activesBean.getPlanType()) {
                    case "1":
                        if (item.getPlanFlag().equals("1"))
                            activeStr = "此商品正在参与打折活动";
                        break;
                    case "2":
                        if (item.getPlanFlag().equals("2"))
                            activeStr = "此商品正在参与满减活动";
                        break;
                    case "3":
                        if (item.getPlanFlag().equals("3"))
                            activeStr = "此商品正在特价促销中";
                        break;
                    case "4":
                        if (item.getPlanFlag().equals("4"))
                            activeStr = "此商品正在参与满赠活动";
                        break;
                    default:
                        activeStr = "";
                        break;
                }
            }
        }

        helper.setText(R.id.tv_product_name, item.getProName())
                .setText(R.id.tv_product_cut, activeStr);
    }

}
