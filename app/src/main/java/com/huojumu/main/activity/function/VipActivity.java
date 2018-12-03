package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huojumu.R;
import com.huojumu.adapter.VipListAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.Vips;
import com.huojumu.utils.Constant;
import com.huojumu.utils.MyDividerDecoration;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/29
 * Description:
 */
public class VipActivity extends BaseActivity {

    @BindView(R.id.recycler_for_vip)
    RecyclerView recyclerView;

    private List<Vips> vips = new ArrayList<>();
    private VipListAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_h5_vip;
    }

    @Override
    protected void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new MyDividerDecoration(this, MyDividerDecoration.VERTICAL));
        adapter = new VipListAdapter(vips);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        NetTool.getVipList(SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<Vips>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<Vips>> response) {
                adapter.setNewData(response.getData());
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
        startActivity(new Intent(VipActivity.this, HomeActivity.class));
        finish();
    }

}
