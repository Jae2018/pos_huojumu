package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.MatsBean;
import com.huojumu.model.Production;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeSelectedAdapter extends BaseItemDraggableAdapter<Production, BaseViewHolder> {

    public HomeSelectedAdapter(@Nullable List<Production> data) {
        super(R.layout.item_home_selected_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Production item) {
        String mat = "";
        double m = 0;
        if (!item.getMats().isEmpty()) {
            for (MatsBean mats : item.getMats()) {
                mat += mats.getMatName() + "、";
                m += mats.getIngredientPrice();
            }
        }
        if (!mat.isEmpty()) {
            mat = mat.substring(0, mat.length() - 1);
        }

        helper.setText(R.id.tv_home_selected_name, item.getProName())
                .setText(R.id.tv_home_selected_number, item.getNumber() + "份")
                .setText(R.id.tv_home_selected_scale, "规格：" + item.getScaleStr())
                .setText(R.id.tv_home_selected_addon, "加料：" + (mat.isEmpty() ? "无" : mat))
                .setText(R.id.tv_home_selected_taste, "口味：" + item.getTasteStr())
//                .setText(R.id.tv_home_selected_price, "单价：" + ((item.getIsBargain() != null) && (item.getIsBargain().equals("1")) ? item.getPrice() : item.getOrigionPrice()))
                .setText(R.id.tv_home_selected_msg, "备注：" + (item.getAddon().isEmpty() ? "无" : item.getAddon()));
//                .setText(R.id.tv_home_selected_cost, "" + (((item.getIsBargain() != null) && (item.getIsBargain().equals("1")) ? item.getPrice() : item.getOrigionPrice()) * item.getNumber() + m));
    }
}
