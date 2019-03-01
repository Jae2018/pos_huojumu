package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.InventoryList;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description: 盘点列表
 */
public class WorkInventoryAdapter extends BaseQuickAdapter<InventoryList.RowsBean, BaseViewHolder> {


    public WorkInventoryAdapter(@Nullable List<InventoryList.RowsBean> data) {
        super(R.layout.item_inventory_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InventoryList.RowsBean item) {
        helper.setText(R.id.tv_inventory_no, String.format("盘点编号：%s", item.getCheckNo().substring(item.getCheckNo().length() - 4)))
                .setText(R.id.tv_inventory_creator, String.format("发起人：%s", item.getCreator()))
                .setText(R.id.tv_inventory_finisher, String.format("执行人：%s", item.getExecutor()))
                .setText(R.id.tv_inventory_start_time, String.format("发起日期：%s", item.getCreateTime()))
                .setText(R.id.tv_inventory_end_time, String.format("执行日期：%s", item.getExecutiveTime()));
    }
}
