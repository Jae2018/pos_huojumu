package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huojumu.R;
import com.huojumu.adapter.OrderListAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrdersList;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class OrdersListActivity extends BaseActivity {

    @BindView(R.id.recycler_orders)
    RecyclerView recyclerView;

    private OrderListAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_orders_list;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new OrderListAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        NetTool.getStoreOrders(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.ENT_ID), SpUtil.getInt(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<OrdersList>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<OrdersList>> response) {
                adapter.setNewData(response.getData());
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
        startActivity(new Intent(OrdersListActivity.this, HomeActivity.class));
        finish();
    }

}
