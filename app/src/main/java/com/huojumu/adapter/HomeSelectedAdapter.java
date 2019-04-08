package com.huojumu.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        helper.setText(R.id.tv_home_selected_name, item.getProName() + "  " + item.getScaleStr() + " * " + item.getNumber() + " " + item.getPrice());

        if (!item.getMats().isEmpty()) {
            for (MatsBean mats : item.getMats()) {
                TextView t = new TextView(mContext);
                t.setText(String.format(mContext.getString(R.string.selected_linear_one), mats.getMatName(), item.getNumber(), mats.getIngredientPrice()));
                t.setPadding(8, 4, 8, 4);
                t.setTextColor(Color.WHITE);
                ((LinearLayout) helper.getView(R.id.linear_mats)).addView(t);
            }
        }
    }
}
