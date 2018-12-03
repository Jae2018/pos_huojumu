package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.Material;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/29
 * Description:
 */
public class MaterialAdapter extends BaseQuickAdapter<Material.RowsBean, BaseViewHolder> {

    public MaterialAdapter(@Nullable List<Material.RowsBean> data) {
        super(R.layout.item_for_material, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Material.RowsBean item) {
        helper.setText(R.id.tv_material_name, String.format("名称：%s", item.getMatName()))
                .setText(R.id.tv_material_weight, String.format("单位：%s", item.getUnitName()))
                .setText(R.id.tv_material_operate, String.format("剩余量：%s", item.getStocks()));
    }
}
