package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.VipListBean;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/9
 * Description:
 */
public class VipListAdapter extends BaseQuickAdapter<VipListBean.RowsBean, BaseViewHolder> {

    public VipListAdapter(@Nullable List<VipListBean.RowsBean> data) {
        super(R.layout.item_for_vips, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipListBean.RowsBean item) {
        helper.setText(R.id.tv_vips_name, String.format("姓名：%s", item.getLoginName()))
                .setText(R.id.tv_vips_mobile, String.format("手机号：%s", item.getMobile()))
                .setText(R.id.tv_vips_level, String.format("会员等级：%s", item.getLevel()))
                .setText(R.id.tv_vips_add_time, String.format("注册时间：%s", item.getRegisterTime()))
                .setText(R.id.tv_vips_cur_score, String.format("现有积分：%s", item.getScore()))
                .setText(R.id.tv_vips_all_score, String.format("总积分：%s", item.getSumScore()));
    }
}
