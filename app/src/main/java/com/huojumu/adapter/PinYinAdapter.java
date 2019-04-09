package com.huojumu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;

import java.util.List;

public class PinYinAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PinYinAdapter(@Nullable List<String> data) {
        super(R.layout.item_pin_yin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_pinyin, item);
    }
}
