package com.huojumu.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.SmallType;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeTypeAdapter extends BaseQuickAdapter<SmallType, BaseViewHolder> {

    public HomeTypeAdapter(@Nullable List<SmallType> data) {
        super(R.layout.item_home_type_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmallType item) {
        helper.setText(R.id.tv_small_type, item.getTypeName())
                .setTextColor(R.id.tv_small_type, item.isSelected() ? Color.WHITE : Color.BLACK)
                .setBackgroundRes(R.id.tv_small_type, item.isSelected() ? R.drawable.home_btn_page : R.drawable.type_btn_unselected);
//                .setBackgroundColor(R.id.tv_small_type, item.isSelected() ? mContext.getResources().getColor(R.color.btn_selected) : mContext.getResources().getColor(R.color.btn_unselected));
    }
}
