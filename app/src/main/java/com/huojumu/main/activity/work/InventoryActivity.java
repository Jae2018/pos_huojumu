package com.huojumu.main.activity.work;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.WorkInventoryAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.InventoryList;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description: 盘点页面
 */
public class InventoryActivity extends BaseActivity {


    @BindView(R.id.recycler_inventory)
    RecyclerView recyclerView;
    private List<InventoryList.RowsBean> rowsBeanList;
    private WorkInventoryAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_inventory;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new WorkInventoryAdapter(rowsBeanList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(InventoryActivity.this, InventoryDetailActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        NetTool.getInventoryList(SpUtil.getInt(Constant.STORE_ID), 10, new GsonResponseHandler<BaseBean<InventoryList>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<InventoryList> response) {
                rowsBeanList = response.getData().getRows();
                adapter.setNewData(rowsBeanList);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.iv_work_daily_back)
    void back() {
        startActivity(new Intent(InventoryActivity.this, HomeActivity.class));
        finish();
    }

}
