package com.huojumu.main.activity.function;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huojumu.R;
import com.huojumu.adapter.OrderBackContentAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrderBackInfo;
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
    EditText editText;
    @BindView(R.id.tv_order_vip_name)
    TextView nameTv;
    @BindView(R.id.tv_order_vip_date)
    TextView dateTv;
    @BindView(R.id.tv_order_id)
    TextView orderIdTv;
    @BindView(R.id.tv_order_date)
    TextView orderDateTv;
    @BindView(R.id.recycler_order_content)
    RecyclerView contentRecycler;

    private OrderBackContentAdapter adapter;
    private String orderId;

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_back;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        contentRecycler.setLayoutManager(manager);
        adapter = new OrderBackContentAdapter(null);
        contentRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    void getOrderInfo() {
        orderId = editText.getText().toString();
        NetTool.getOrderInfo(orderId, new GsonResponseHandler<BaseBean<OrderBackInfo>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderBackInfo> response) {
                nameTv.setText(response.getData().getMember().getNickname());
                dateTv.setText(response.getData().getMember().getJoinTime());
                orderIdTv.setText(response.getData().getOrderdetail().getOrderid());
                orderDateTv.setText(response.getData().getOrderdetail().getCreateTime());
                adapter.setNewData(response.getData().getOrderdetail().getPros());
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

    }

    private void payBack(String orderId) {
        NetTool.getPayBack(SpUtil.getInt(Constant.STORE_ID), orderId, "POS退单", new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                Toast toast = Toast.makeText(PayBackActivity.this, "退单成功！", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    @OnClick(R.id.iv_search_order)
    void search() {
        getOrderInfo();
    }

    @OnClick(R.id.btn_cancel)
    void back() {
        startActivity(new Intent(PayBackActivity.this, HomeActivity.class));
        finish();
    }

    @OnClick(R.id.btn_commit)
    void commit() {
        if (orderId != null) {
            payBack(orderId);
        }
    }

}
