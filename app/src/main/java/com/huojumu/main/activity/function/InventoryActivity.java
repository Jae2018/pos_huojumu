package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.WorkInventoryAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.InventoryList;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
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
    private List<InventoryList.RowsBean> rowsBeanList = new ArrayList<>();
    private WorkInventoryAdapter adapter;
    //
    private int pageNum = 1, totalPAge;

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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(InventoryActivity.this, InventoryDetailActivity.class);
                i.putExtra("checkId", rowsBeanList.get(position).getCheckId());
            }
        });

    }

    @Override
    protected void initData() {
        getList();
    }

    private void getList() {
        ld2 = new LoadingDialog(this);
        ld2.setLoadingText("加载中,请等待")
                .setFailedText("加载失败，请重试");
        ld2.show();
        NetTool.getInventoryList(SpUtil.getInt(Constant.STORE_ID), pageNum, new GsonResponseHandler<BaseBean<InventoryList>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<InventoryList> response) {
                ld2.close();
                if (response.getData().getTotal() > 0) {
                    rowsBeanList.addAll(response.getData().getRows()) ;
                    adapter.setNewData(rowsBeanList);
                    totalPAge = response.getData().getTotal();
                    pageNum++;
                    adapter.setUpFetchEnable(false);
                }
            }

            @Override
            public void onFailure(int statusCode,String code, String error_msg) {
                ToastUtils.showLong("暂无数据");
                ld2.loadFailed();
                ld2.close();
            }
        });
    }

    @OnClick(R.id.iv_work_daily_back)
    void back() {
        finish();
    }

}
