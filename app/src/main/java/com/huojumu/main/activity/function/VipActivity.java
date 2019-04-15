package com.huojumu.main.activity.function;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huojumu.R;
import com.huojumu.adapter.VipListAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.VipListBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private List<VipListBean.RowsBean> vips = new ArrayList<>();
    private VipListAdapter adapter;
    //
    private int pageNum = 1;

    @Override
    protected int setLayout() {
        return R.layout.activity_h5_vip;
    }

    @Override
    protected void initView() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_v));
        recyclerView.addItemDecoration(decoration);

        adapter = new VipListAdapter(vips);
        recyclerView.setAdapter(adapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getVipList();

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (manager.findLastVisibleItemPosition() + 2 >= manager.getChildCount()) {
                    getVipList();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    protected void initData() {
        getVipList();
    }

    private void getVipList() {
        ld2 = new LoadingDialog(this);
        ld2.show();
        NetTool.getVipList(SpUtil.getInt(Constant.PINPAI_ID), pageNum, new GsonResponseHandler<BaseBean<VipListBean>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<VipListBean> response) {
                ld2.close();
                if (!response.getData().getRows().isEmpty()) {
                    vips.addAll(response.getData().getRows());
                    adapter.setNewData(vips);
                }
                pageNum++;
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode,String code, String error_msg) {
                ld2.close();
            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
//        startActivity(new Intent(VipActivity.this, HomeActivity.class));
        finish();
    }

}
