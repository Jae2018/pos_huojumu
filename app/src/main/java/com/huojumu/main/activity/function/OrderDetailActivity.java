package com.huojumu.main.activity.function;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.adapter.OrderBackContentAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrderDetails;
import com.huojumu.utils.NetTool;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_order_back_name)
    TextView buyer;
    @BindView(R.id.tv_order_back_price)
    TextView priceTv;
    @BindView(R.id.tv_order_back_date)
    TextView dateTv;
    @BindView(R.id.tv_order_back_pay_type)
    TextView payTypeTv;
    @BindView(R.id.recycler_back_detail)
    RecyclerView detailRecycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;

    //
    private String id;
    //明细集合
    private OrderBackContentAdapter contentAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        detailRecycler.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter = new OrderBackContentAdapter(null);
        detailRecycler.setAdapter(contentAdapter);
        id = getIntent().getStringExtra("detailId");
    }

    @Override
    protected void initData() {
        getOrderDetail(id);
    }

    private void getOrderDetail(String orderId) {
        NetTool.getOrderInfo(orderId, new GsonResponseHandler<BaseBean<OrderDetails>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderDetails> response) {
                if (response.getData().getMember() != null) {
                    buyer.setText(String.format("下单客户：%s", response.getData().getMember().getNickname()));
                }
                if (response.getData().getOrderdetail() != null) {
                    contentAdapter.setNewData(response.getData().getOrderdetail().getPros());
                    priceTv.setText(String.format("订单金额：%s元", response.getData().getOrderdetail().getTotalPrice()));
                    dateTv.setText(String.format("订单日期：%s", response.getData().getOrderdetail().getCreateTime()));
                    payTypeTv.setText(String.format("支付方式：%s", response.getData().getOrderdetail().getPayType().equals("010") ? "微信支付" : response.getData().getOrderdetail().getPayType().equals("020") ? "支付宝支付" : "现金支付"));
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
