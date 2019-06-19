package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.OrderListAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrdersList;
import com.huojumu.utils.NetTool;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单查询
 * */
public class OrdersListActivity extends BaseActivity {

    @BindView(R.id.recycler_orders)
    RecyclerView recyclerView;

    //
    private List<OrdersList.RowsBean> rowsBeanList = new ArrayList<>();
    private OrderListAdapter listAdapter;
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
        listAdapter = new OrderListAdapter(null);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (pageNum >= totalPAge) {
                    listAdapter.loadMoreEnd();
                } else {
                    getList();
                }
            }
        }, recyclerView);
        listAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                listAdapter.setUpFetchEnable(true);
                rowsBeanList.clear();
                pageNum = 1;
                getList();
            }
        });

        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(OrdersListActivity.this, OrderDetailActivity.class);
                i.putExtra("detailId", listAdapter.getData().get(position).getOrdId());
                startActivity(i);
            }
        });
    }

    @Override
    protected void initData() {
        getList();
    }

    private void getList() {
        progressDialog.show();
        NetTool.getStoreOrderList(pageNum, new GsonResponseHandler<BaseBean<OrdersList>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrdersList> response) {
                rowsBeanList.addAll(response.getData().getRows());
                listAdapter.setNewData(rowsBeanList);
                totalPAge = response.getData().getTotal();
                pageNum++;
                listAdapter.setUpFetching(false);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode,String code, String error_msg) {
                ToastUtils.showLong(error_msg);
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
