package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.Vips;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/9
 * Description:
 */
public class VipListAdapter extends BaseQuickAdapter<Vips, BaseViewHolder> {

    public VipListAdapter(@Nullable List<Vips> data) {
        super(R.layout.item_for_vips, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Vips item) {
        helper.setText(R.id.tv_vips_name, item.getName()).setText(R.id.tv_vips_sex, item.getSex()).setText(R.id.tv_vips_grade, item.getGrade());
    }
}
