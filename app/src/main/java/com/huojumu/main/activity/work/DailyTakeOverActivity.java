package com.huojumu.main.activity.work;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.WorkDailyAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.function.OrderDetailActivity;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.main.dialogs.DialogInterface;
import com.huojumu.model.BaseBean;
import com.huojumu.model.DailyInfo;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SocketTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.util.ArrayList;
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

    @BindView(R.id.tv_work_daily_takeover)
    TextView title;
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
    @BindView(R.id.btn_work_daily_ok)
    Button commit;
    @BindView(R.id.btn_work_daily_cancel)
    Button cancel;

    private WorkDailyAdapter dailyAdapter;
    private List<DailyInfo.OrdersBean.RowsBean> rowsBeans = new ArrayList<>();
    private int page = 1;
    private CertainDialog certainDialog;
    private long timestamp;
    private int types;
    private int num = 0;
    private double s1, s2, s3, s4;
    private String lastDate;

    @Override
    protected int setLayout() {
        return R.layout.activity_daily_take_over;
    }

    @Override
    protected void initView() {
        types = getIntent().getIntExtra("type", 1);
        title.setText(types == 1 ? "交班" : "日结");
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
                if (types == 1) {
                    getTakeOverInfo();
                } else {
                    getDailyInfo();
                }
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

        dailyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(DailyTakeOverActivity.this, OrderDetailActivity.class);
                i.putExtra("detailId", dailyAdapter.getData().get(position).getOrderId());
                startActivity(i);
            }
        });

        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    protected void initData() {
        if (types == 1) {
            getTakeOverInfo();
        } else {
            getDailyInfo();
        }
    }

    @OnClick(R.id.btn_work_daily_cancel)
    void Cancel() {
        finish();
    }

    @OnClick(R.id.btn_work_daily_ok)
    void Ok() {
        if (types == 1) {
            certainDialog = new CertainDialog(this, this, "注意！", "确认要交班吗？");
        } else {
            certainDialog = new CertainDialog(this, this, "注意！", "确认要日结吗？");
        }
        certainDialog.show();
    }

    @Override
    public void OnUsbCallBack(String name) {

    }

    @Override
    public void OnDialogOkClick(int type, double earn, double cost, double charge, String name) {
        if (types == 1) {
            //交班确认回调
            TakeOver();
        } else {
            daily();
        }
        PrinterUtil.printDaily(types, commissionTv.getText().toString(), earn2.getText().toString(), earn1.getText().toString(), num, SpUtil.getString(Constant.WORKER_NAME), lastDate);
    }

    private void getTakeOverInfo() {
        progressDialog.show();
        NetTool.getDailyInfo(SpUtil.getInt(Constant.STORE_ID), SpUtil.getInt(Constant.PINPAI_ID), page, 0, new GsonResponseHandler<BaseBean<DailyInfo>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<DailyInfo> response) {
                lastDate = response.getData().getLastEndTime();
                num = response.getData().getOrders().getTotal();
                if (page == 1) {
                    rowsBeans.clear();
                }
                rowsBeans.addAll(response.getData().getOrders().getRows());
                if (rowsBeans.isEmpty()) {
                    ToastUtils.showLong("无数据");
                } else {
                    dailyAdapter.setNewData(rowsBeans);
                    s1 = response.getData().getSaleData().getReal();
                    earn1.setText(String.format(Locale.CHINA, "实收金额：%.2f", s1));
                    s2 = response.getData().getSaleData().getVirtual();
                    earn2.setText(String.format(Locale.CHINA, "虚收金额：%.2f", s2));
                    s3 = response.getData().getPushMoneyData().getPushMoney();
                    sellTv.setText(String.format(Locale.CHINA, "提成：%.4f", s3));
                    s4 = response.getData().getSaleData().getTotal();
                    commissionTv.setText(String.format(Locale.CHINA, "营业额：%.2f", s4));
                    timestamp = response.getData().getTimestamp();
                    if (page < response.getData().getOrders().getPageNum()) {
                        page++;
                    }
                }

                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
                if (!SocketTool.getInstance().isAlive()) {
                    ToastUtils.showLong("网络已断开，请检查联网状态");
                } else {
                    ToastUtils.showLong(error_msg);
                }
            }
        });
    }

    private void getDailyInfo() {
        progressDialog.show();
        NetTool.getSettlementInfo(SpUtil.getInt(Constant.STORE_ID), page, new GsonResponseHandler<BaseBean<DailyInfo>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<DailyInfo> response) {
                lastDate = response.getData().getLastEndTime();
                num = response.getData().getOrders().getTotal();
                if (page == 1) {
                    rowsBeans.clear();
                }
                rowsBeans.addAll(response.getData().getOrders().getRows());
                dailyAdapter.setNewData(rowsBeans);
                s1 = response.getData().getSaleData().getReal();
                earn1.setText(String.format(Locale.CHINA, "总实收金额：%.2f", s1));
                s2 = response.getData().getSaleData().getVirtual();
                earn2.setText(String.format(Locale.CHINA, "总虚收金额：%.2f", s2));
                s4 = response.getData().getSaleData().getTotal();
                commissionTv.setText(String.format(Locale.CHINA, "总销售金额：%.2f", s4));
                commit.setEnabled(s4 != 0);

                timestamp = response.getData().getTimestamp();
                if (page < response.getData().getOrders().getPageNum()) {
                    page++;
                }
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
                commit.setEnabled(false);
                if (SocketTool.getInstance().isAlive()) {
                    ToastUtils.showLong(error_msg);
                } else {
                    ToastUtils.showLong("网络已断开，请检查联网状态");
                }
            }
        });
    }

    private void TakeOver() {
        progressDialog.show();
        Log.e("work", "TakeOver: "+timestamp );
        NetTool.takeOver(timestamp, new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                if (response.getMsg().equals("yes")) {
                    certainDialog.cancel();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtils.showLong(response.getMsg());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
                if (SocketTool.getInstance().isAlive()) {
                    ToastUtils.showLong(error_msg);
                } else {
                    ToastUtils.showLong("网络已断开，请检查联网状态");
                }
            }
        });
    }

    private void daily() {
        progressDialog.show();
        NetTool.settlement(SpUtil.getInt(Constant.STORE_ID), new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                if (response.getMsg().equals("yes")) {
                    certainDialog.cancel();
                    setResult(RESULT_OK);
                    ToastUtils.showLong("日结成功！30秒后系统将自动关闭");
                    finish();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
                if (SocketTool.getInstance().isAlive()) {
                    ToastUtils.showLong(error_msg);
                } else {
                    ToastUtils.showLong("网络已断开，请检查联网状态");
                }
            }
        });
    }

}
