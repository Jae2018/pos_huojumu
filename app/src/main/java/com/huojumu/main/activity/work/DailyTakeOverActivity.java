package com.huojumu.main.activity.work;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.data.OrderDetail;
import com.data.OrderSave;
import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.adapter.WorkDailyAdapter;
import com.huojumu.adapter.WorkDailyDetailAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description: 日结、交班页面
 */
public class DailyTakeOverActivity extends BaseActivity {

    @BindView(R.id.iv_work_daily_avatar)
    ImageView avatarIv;
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
    private WorkDailyDetailAdapter detailAdapter;
    private int type;
    private double totalSell,totalEarn1, totalEarn2;

    @Override
    protected int setLayout() {
        return R.layout.activity_daily_take_over;
    }

    @Override
    protected void initView() {
        nameTv.setText(SpUtil.getString(Constant.WORKER_NAME));
        dateTv.setText(DateFormat.getDateInstance().format(new Date()));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        dailyRecycler.setLayoutManager(manager);
        dailyAdapter = new WorkDailyAdapter(null);
        detailAdapter = new WorkDailyDetailAdapter(null);
        dailyRecycler.setAdapter(dailyAdapter);

    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra(Constant.TYPE, 1);
        List<OrderSave> orderSaveList = MyApplication.getDb().getOrderDao().getOrderSaveList();
        List<OrderDetail> detailList = MyApplication.getDb().getDetailDao().getOrderSaveList();
        dailyAdapter.setNewData(orderSaveList);
        detailAdapter.setNewData(detailList);

        for (OrderSave orderSave : orderSaveList) {
            totalSell += orderSave.getPrice();
            totalEarn1 += orderSave.getEarn1();
            totalEarn2 += orderSave.getEarn2();
        }
        earn1.setText(String.valueOf(totalEarn1));
        earn2.setText(String.valueOf(totalEarn2));
        sellTv.setText(String.valueOf(totalSell));
        commissionTv.setText(String.valueOf(totalSell / 10));
    }

    @OnClick(R.id.btn_work_daily_type1)
    void onType1Show() {
        dailyRecycler.setAdapter(dailyAdapter);
    }

    @OnClick(R.id.btn_work_daily_type2)
    void onType2Show() {
        dailyRecycler.setAdapter(detailAdapter);
    }

    @OnClick(R.id.btn_work_daily_cancel)
    void Cancel() {
        finish();
    }

    @OnClick(R.id.btn_work_daily_ok)
    void Ok() {
        //上传报表
        //清空表数据、清空缓存，调到登录页面
        if (type == 2) {//交班清空数据库内容
            MyApplication.getDb().getOrderDao().deleteAll();
            startActivity(new Intent(DailyTakeOverActivity.this, LoginActivity.class));
        }
        finish();
    }

//    @OnClick(R.id.iv_back)
//    void back() {
//        startActivity(new Intent(DailyTakeOverActivity.this, HomeActivity.class));
//        finish();
//    }

}
