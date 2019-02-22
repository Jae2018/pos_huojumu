package com.huojumu.main.activity.work;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.adapter.WorkDailyAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.main.dialogs.DialogInterface;
import com.huojumu.model.BaseBean;
import com.huojumu.model.DailyInfo;
import com.huojumu.model.OrderNoList;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description: 日结、交班页面
 */
public class DailyTakeOverActivity extends BaseActivity implements DialogInterface {

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_work_daily_name)
    TextView nameTv;//名字
    @BindView(R.id.tv_work_daily_date)
    TextView dateTv;//日期
    @BindView(R.id.recycler_work_daily)
    RecyclerView dailyRecycler;
    @BindView(R.id.tv_work_daily_sell)
    TextView sellTv;//销售额
    @BindView(R.id.tv_work_daily_commission)
    TextView commissionTv;//提成
    @BindView(R.id.tv_daily_earn1)
    TextView earn1;
    @BindView(R.id.tv_daily_earn2)
    TextView earn2;

    private WorkDailyAdapter dailyAdapter;
    private List<DailyInfo.OrdersBean.RowsBean> rowsBeans = new ArrayList<>();
    private int page = 1;
    private CertainDialog certainDialog;
    private long timestamp;

    @Override
    protected int setLayout() {
        return R.layout.activity_daily_take_over;
    }

    @Override
    protected void initView() {
        nameTv.setText(String.format("员工：%s", SpUtil.getString(Constant.WORKER_NAME)));
        dateTv.setText(String.format("日期：%s", PrinterUtil.getCNDate()));

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        dailyRecycler.setLayoutManager(manager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_v));
        dailyRecycler.addItemDecoration(itemDecoration);

        dailyAdapter = new WorkDailyAdapter(null);
        dailyRecycler.setAdapter(dailyAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                rowsBeans.clear();
                initData();
            }
        });

        dailyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == dailyAdapter.getItemCount()) {
                    initData();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected void initData() {
        NetTool.getDailyInfo(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.PINPAI_ID), page, new GsonResponseHandler<BaseBean<DailyInfo>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<DailyInfo> response) {
                rowsBeans.addAll(response.getData().getOrders().getRows());
                dailyAdapter.setNewData(rowsBeans);
                earn1.setText(String.format(Locale.CHINA, "实收：%s", response.getData().getSaleData().getReal()));
                earn2.setText(String.format(Locale.CHINA, "虚收：%s", response.getData().getSaleData().getVirtual()));
                sellTv.setText(String.format(Locale.CHINA, "提成：%s", response.getData().getSaleData().getTotal()));
                commissionTv.setText(String.format(Locale.CHINA, "营业额：%s", response.getData().getPushMoneyData().getPushMoney()));
                timestamp = response.getData().getTimestamp();
                if (page < response.getData().getOrders().getPageNum()) {
                    page++;
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

    }

    @OnClick(R.id.btn_work_daily_type1)
    void onType1Show() {
        dailyRecycler.setAdapter(dailyAdapter);
    }

    @OnClick(R.id.btn_work_daily_type2)
    void onType2Show() {

    }

    @OnClick(R.id.btn_work_daily_cancel)
    void Cancel() {
        startActivity(new Intent(DailyTakeOverActivity.this, HomeActivity.class));
    }

    @OnClick(R.id.btn_work_daily_ok)
    void Ok() {
        certainDialog = new CertainDialog(this, this, "注意！", "确认要交班吗？");
        certainDialog.show();
    }

    @Override
    public void OnDialogOkClick(double value, String name) {
        //交班确认回调
        TakeOver();
    }

    @Override
    public void OnTasteClick(int type, int id, int number, String name) {

    }

    @Override
    public void OnDialogCancelClick(int value) {
        certainDialog.cancel();
    }

    private void TakeOver(){
        NetTool.takeOver(timestamp, new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                if (response.getMsg().equals("yes")) {
                    certainDialog.cancel();
                    startActivity(new Intent(DailyTakeOverActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ToastUtils.showLong(response.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
}
