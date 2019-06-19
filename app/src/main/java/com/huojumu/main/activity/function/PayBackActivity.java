package com.huojumu.main.activity.function;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.OrderBackContentAdapter;
import com.huojumu.adapter.OrderEnableBackAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.dialogs.CertainDialog;
import com.huojumu.listeners.DialogInterface;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrderDetails;
import com.huojumu.model.OrderEnableBackBean;
import com.huojumu.model.WorkBean;
import com.huojumu.utils.Constant;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SpUtil;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.annotation.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/11/6
 * Description: 退款，反结算
 */
public class PayBackActivity extends BaseActivity implements DialogInterface {

    @BindView(R.id.et_order_id)
    EditText editText;
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
    @BindView(R.id.tv_order_back_operator)
    TextView operator;
    @BindView(R.id.tv_order_back_operator_join)
    TextView join;

    //可退单集合
    private OrderEnableBackAdapter backAdapter;
    //明细集合
    private OrderBackContentAdapter contentAdapter;
    //
    private String id, payType;
    //
    private CertainDialog dialog;
    private OrderDetails details;
    private float p;//当前班次金额
    private double total;//退单的金额
    private int num;//当前班次单数

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
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_v, null));
        backRecycler.addItemDecoration(decoration);
        backRecycler.setAdapter(backAdapter);
        detailRecycler.setAdapter(contentAdapter);

        backAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                id = backAdapter.getData().get(position).getId();
                payType = backAdapter.getData().get(position).getPayType();
                getOrderDetail(id);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderDetail(id);
            }
        });

        p = SpUtil.getFloat(Constant.WORK_P);
        num = SpUtil.getInt(Constant.ORDER_NUM);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() == 4) {
                    getEnableBackOrderList();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void OnUsbCallBack(String name) {

    }

    private void getEnableBackOrderList() {
        progressDialog.show();
        NetTool.getEnableBackOrderList(SpUtil.getInt(Constant.STORE_ID), editText.getText().toString(), new GsonResponseHandler<BaseBean<OrderEnableBackBean>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderEnableBackBean> response) {
                backAdapter.setNewData(response.getData().getOrders());
                progressDialog.dismiss();
                hideSoftKeyboard(PayBackActivity.this, editText);
                editText.setText("");
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                ToastUtils.showLong("订单信息有误！");
                progressDialog.dismiss();
                editText.setText("");
            }
        });
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, @NotNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void getOrderDetail(String orderId) {
        progressDialog.show();
        NetTool.getOrderInfo(orderId, new GsonResponseHandler<BaseBean<OrderDetails>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderDetails> response) {
                if (response.getData().getOrderdetail() != null) {
                    details = response.getData();
                    total = response.getData().getOrderdetail().getTotalPrice();
                    contentAdapter.setNewData(response.getData().getOrderdetail().getPros());
                    priceTv.setText(String.format("订单金额：%s元", total));
                    dateTv.setText(String.format("订单日期：%s", response.getData().getOrderdetail().getCreateTime()));
                    payTypeTv.setText(String.format("支付方式：%s", response.getData().getOrderdetail().getPayType().equals("900") ? "现金支付" : response.getData().getOrderdetail().getPayType().equals("010") ? "微信支付" : "支付宝支付"));
                }
                if (response.getData().getOperator() != null) {
                    operator.setText(String.format("操作员工：%s", response.getData().getOperator().getNickname()));
                    join.setText(String.format("员工入职时间：%s", response.getData().getOperator().getJoinTime()));
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                ToastUtils.showLong(error_msg);
                progressDialog.dismiss();
            }
        });
    }

//    @OnClick(R.id.iv_search_order)
//    void search() {
//        getEnableBackOrderList();
//    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.btn_cancel)
    void cancelBack() {
        finish();
    }

    @OnClick(R.id.btn_commit)
    void commitBack() {
        if (dialog == null) {
            dialog = new CertainDialog(this, this, "注意！", "确定要退单吗？");
        }
        dialog.show();
    }

    @Override
    public void OnDialogOkClick(int type, double earn, double cost, double charge, String name) {
        dialog.cancel();
        progressDialog.show();
        NetTool.getPayBack(SpUtil.getInt(Constant.STORE_ID), id, payType, new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, final BaseBean<String> response) {
                if (response.getCode().equals("0")) {
                    clearRight();
                    PrinterUtil.printPayBack(details, response.getData());
                    float price = (float) (p - total);
                    num = num - 1;
                    EventBus.getDefault().post(new WorkBean(num, price));
                    ToastUtils.showLong("退单成功！");
                } else {
                    ToastUtils.showLong(response.getMsg());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
                if (!code.equals("0")) {
                    ToastUtils.showLong(error_msg);
                }
            }
        });
    }

    private void clearRight() {
        buyer.setText("下单客户：");
        contentAdapter.setNewData(null);
        priceTv.setText("订单金额：");
        dateTv.setText("订单日期：");
        payTypeTv.setText("支付方式：");
        operator.setText(String.format("操作员工：%s", ""));
        join.setText(String.format("员工入职时间：%s", ""));
        backAdapter.setNewData(null);
    }

}
