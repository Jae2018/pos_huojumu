package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.OrderListAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrdersList;
import com.huojumu.utils.NetTool;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class OrdersListActivity extends BaseActivity {

    @BindView(R.id.recycler_orders)
    RecyclerView recyclerView;

    //
    private List<OrdersList.RowsBean> rowsBeanList = new ArrayList<>();
    private OrderListAdapter adapter;
    //
    private int pageNum = 1, totalPAge;

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
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (pageNum >= totalPAge) {
                    adapter.loadMoreEnd();
                } else {
                    getList();
                }
            }
        }, recyclerView);
        adapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                adapter.setUpFetchEnable(true);
                rowsBeanList.clear();
                pageNum = 1;
                getList();
            }
        });
    }

    @Override
    protected void initData() {
        getList();
    }

    private void getList() {
        NetTool.getStoreOrderList(pageNum, new GsonResponseHandler<BaseBean<OrdersList>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrdersList> response) {
                rowsBeanList.addAll(response.getData().getRows());
                adapter.setNewData(rowsBeanList);
                totalPAge = response.getData().getTotal();
                pageNum++;
                adapter.setUpFetching(false);
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
