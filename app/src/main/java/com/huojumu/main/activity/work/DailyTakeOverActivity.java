package com.huojumu.main.activity.work;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.huojumu.MyApplication;
import com.huojumu.R;
import com.huojumu.adapter.WorkDailyAdapter;
import com.huojumu.adapter.WorkDailyDetailAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrderDetail;
import com.huojumu.model.OrderNoList;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.text.DateFormat;
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
//    @BindView(R.id.tv_work_daily_commission)
//    TextView commissionTv;//提成
    @BindView(R.id.tv_daily_earn1)
    TextView earn1;
    @BindView(R.id.tv_daily_earn2)
    TextView earn2;

    private WorkDailyAdapter dailyAdapter;
    private WorkDailyDetailAdapter detailAdapter;
    private int type;
    private double totalSell, totalEarn1, totalEarn2;
    private List<OrderNoList.RowsBean> orderSaveList;

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
        dailyAdapter.setNewData(orderSaveList);

        NetTool.getOrderNoList(new GsonResponseHandler<BaseBean<OrderNoList>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderNoList> response) {
                orderSaveList = response.getData().getRows();
                dailyAdapter.setNewData(orderSaveList);
                for (OrderNoList.RowsBean orderSave : orderSaveList) {
                    Log.e("price", orderSave.getTotalPrice()+"" );
                    totalSell += orderSave.getTotalPrice();
                    if ("900".equals(orderSave.getPayType())) {
                        totalEarn1 += orderSave.getTotalPrice();
                    } else {
                        totalEarn2 += orderSave.getTotalPrice();
                    }
                }
                earn1.setText(String.format(Locale.CHINA,"%.2f",totalEarn1));
                earn2.setText(String.format(Locale.CHINA,"%.2f",totalEarn2));
                sellTv.setText(String.format(Locale.CHINA,"%.2f",totalSell));
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

        NetTool.getOrderInfoList(SpUtil.getInt(Constant.STORE_ID), SpUtil.getString(Constant.ENT_ID), SpUtil.getString(Constant.PINPAI_ID), new GsonResponseHandler<BaseBean<List<OrderDetail>>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<List<OrderDetail>> response) {

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
        dailyRecycler.setAdapter(detailAdapter);
    }

    @OnClick(R.id.btn_work_daily_cancel)
    void Cancel() {
        startActivity(new Intent(DailyTakeOverActivity.this, HomeActivity.class));
    }

    @OnClick(R.id.btn_work_daily_ok)
    void Ok() {
        //上传报表
        //清空表数据、清空缓存，调到登录页面
        if (type == 2) {//交班清空数据库内容
            MyApplication.getDb().getOrderDao().deleteAll();
            MyApplication.getDb().getDetailDao().deleteAll();
            startActivity(new Intent(DailyTakeOverActivity.this, LoginActivity.class));
        }
        finish();
    }

}

//
//package com.huojumu.main.activity.work;
//
//import android.content.Intent;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.data.OrderDetail;
//import com.data.OrderSave;
//import com.huojumu.MyApplication;
//import com.huojumu.R;
//import com.huojumu.adapter.WorkDailyAdapter;
//import com.huojumu.adapter.WorkDailyDetailAdapter;
//import com.huojumu.base.BaseActivity;
//import com.huojumu.main.activity.home.HomeActivity;
//import com.huojumu.main.activity.login.LoginActivity;
//import com.huojumu.utils.Constant;
//import com.huojumu.utils.SpUtil;
//
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * @author : Jie
// * Date: 2018/10/19
// * Description: 日结、交班页面
// */
//public class DailyTakeOverActivity extends BaseActivity {
//
//    @BindView(R.id.iv_work_daily_avatar)
//    ImageView avatarIv;
//    @BindView(R.id.tv_work_daily_name)
//    TextView nameTv;//名字
//    @BindView(R.id.tv_work_daily_date)
//    TextView dateTv;//日期
//    @BindView(R.id.recycler_work_daily)
//    RecyclerView dailyRecycler;
//    @BindView(R.id.tv_work_daily_sell)
//    TextView sellTv;//销售额
////    @BindView(R.id.tv_work_daily_commission)
////    TextView commissionTv;//提成
//    @BindView(R.id.tv_daily_earn1)
//    TextView earn1;
//    @BindView(R.id.tv_daily_earn2)
//    TextView earn2;
//
//    private WorkDailyAdapter dailyAdapter;
//    private WorkDailyDetailAdapter detailAdapter;
//    private int type;
//    private double totalSell, totalEarn1, totalEarn2;
//
//    @Override
//    protected int setLayout() {
//        return R.layout.activity_daily_take_over;
//    }
//
//    @Override
//    protected void initView() {
//        nameTv.setText(SpUtil.getString(Constant.WORKER_NAME));
//        dateTv.setText(DateFormat.getDateInstance().format(new Date()));
//
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        dailyRecycler.setLayoutManager(manager);
//        dailyAdapter = new WorkDailyAdapter(null);
//        detailAdapter = new WorkDailyDetailAdapter(null);
//        dailyRecycler.setAdapter(dailyAdapter);
//
//    }
//
//    @Override
//    protected void initData() {
//        type = getIntent().getIntExtra(Constant.TYPE, 1);
//        List<OrderSave> orderSaveList = MyApplication.getDb().getOrderDao().getOrderSaveList();
//        List<OrderDetail> detailList = MyApplication.getDb().getDetailDao().getOrderSaveList();
//        dailyAdapter.setNewData(orderSaveList);
//        detailAdapter.setNewData(detailList);
//
//        for (OrderDetail detail : detailList) {
//            Log.e("work", detail.getProName() + ":" + detail.getNumber());
//        }
//
//        for (OrderSave orderSave : orderSaveList) {
//            Log.e("price", orderSave.getPrice()+"" );
//            totalSell += orderSave.getPrice();
//            totalEarn1 += orderSave.getEarn1();
//            totalEarn2 += orderSave.getEarn2();
//        }
//        earn1.setText(String.format(Locale.CHINA,"%.2f",totalEarn1));
//        earn2.setText(String.format(Locale.CHINA,"%.2f",totalEarn2));
//        sellTv.setText(String.format(Locale.CHINA,"%.2f",totalSell));
//        Log.e("daily", totalSell+"" );
////        commissionTv.setText(String.format(Locale.CHINA,"%.2f",totalSell / 10));
//    }
//
//    @OnClick(R.id.btn_work_daily_type1)
//    void onType1Show() {
//        dailyRecycler.setAdapter(dailyAdapter);
//    }
//
//    @OnClick(R.id.btn_work_daily_type2)
//    void onType2Show() {
//        dailyRecycler.setAdapter(detailAdapter);
//    }
//
//    @OnClick(R.id.btn_work_daily_cancel)
//    void Cancel() {
//        startActivity(new Intent(DailyTakeOverActivity.this, HomeActivity.class));
//    }
//
//    @OnClick(R.id.btn_work_daily_ok)
//    void Ok() {
//        //上传报表
//        //清空表数据、清空缓存，调到登录页面
//        if (type == 2) {//交班清空数据库内容
//            MyApplication.getDb().getOrderDao().deleteAll();
//            MyApplication.getDb().getDetailDao().deleteAll();
//            startActivity(new Intent(DailyTakeOverActivity.this, LoginActivity.class));
//        }
//        finish();
//    }
//
////    @OnClick(R.id.iv_back)
////    void back() {
////        startActivity(new Intent(DailyTakeOverActivity.this, HomeActivity.class));
////        finish();
////    }
//
//}
