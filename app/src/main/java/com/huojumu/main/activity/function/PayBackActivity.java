package com.huojumu.main.activity.function;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.OrderBackContentAdapter;
import com.huojumu.adapter.OrderEnableBackAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrderDetails;
import com.huojumu.model.OrderEnableBackBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/11/6
 * Description: 退款，反结算
 */
public class PayBackActivity extends BaseActivity {

    @BindView(R.id.et_order_id)
    EditText editText;//ID
    @BindView(R.id.recycler_order_back)
    RecyclerView backRecycler;
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

    //可退单集合
    private OrderEnableBackAdapter backAdapter;
    //明细集合
    private OrderBackContentAdapter contentAdapter;
    //
    private String id;

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_back;
    }

    @Override
    protected void initView() {
        backRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailRecycler.setLayoutManager(new LinearLayoutManager(this));
        backAdapter = new OrderEnableBackAdapter(null);
        contentAdapter = new OrderBackContentAdapter(null);
        backRecycler.setAdapter(backAdapter);
        detailRecycler.setAdapter(contentAdapter);
        backAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                id = backAdapter.getData().get(position).getId();
                getOrderDetail(id);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderDetail(id);
            }
        });
    }

    @Override
    protected void initData() {


    }

    private void getEnableBackOrderList() {
        NetTool.getEnableBackOrderList(SpUtil.getInt(Constant.STORE_ID), editText.getText().toString(), new GsonResponseHandler<BaseBean<OrderEnableBackBean>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderEnableBackBean> response) {
                if (response.getData() == null) {
                    ToastUtils.showLong("无对应订单数据！");
                } else {
                    backAdapter.setNewData(response.getData().getOrders());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
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
                    payTypeTv.setText(response.getData().getOrderdetail().getPayType().equals("010") ? "微信支付" : "支付宝支付");
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

    @OnClick(R.id.iv_search_order)
    void search() {
        getEnableBackOrderList();
    }

    @OnClick(R.id.iv_back)
    void back() {
        startActivity(new Intent(PayBackActivity.this, HomeActivity.class));
        finish();
    }


}
