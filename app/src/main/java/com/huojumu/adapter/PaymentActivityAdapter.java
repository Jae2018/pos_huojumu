package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.model.ActivesBean;

import java.util.List;

public class PaymentActivityAdapter extends BaseQuickAdapter<ActivesBean, BaseViewHolder> {

    public PaymentActivityAdapter(@Nullable List<ActivesBean> data) {
        super(R.layout.item_payment_actives, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivesBean item) {
        helper.setText(R.id.tv_active_name, item.getPlanName());
        if (item.getStartDate() != null) {
            helper.setText(R.id.tv_active_start, "活动开始时间：" + item.getStartDate())
                    .setText(R.id.tv_active_end, "活动结束时间：" + item.getEndDate());
        }
    }
}
