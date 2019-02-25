package com.huojumu.main.activity.function;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.data.OrderDetail;
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

import java.util.List;

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
    private List<OrderBackInfo.OrderdetailBean.ProsBean> list;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_back;
    }

    @Override
    protected void initView() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        contentRecycler.setLayoutManager(manager);
        adapter = new OrderBackContentAdapter(null);
        contentRecycler.setAdapter(adapter);

        contentRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // 当不滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();

                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                        //加载更多
                        getOrderList();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                isSlidingUpward = dy > 0;
            }
        });
    }

    @Override
    protected void initData() {

//        dao = MyApplication.getDb().getOrderDao();
//        detailDao = MyApplication.getDb().getDetailDao();
    }

    private void getOrderList() {

    }

    void getOrderInfo() {
        orderId = editText.getText().toString();
        NetTool.getOrderInfo(orderId, new GsonResponseHandler<BaseBean<OrderBackInfo>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderBackInfo> response) {
                list = response.getData().getOrderdetail().getPros();
                nameTv.setText(response.getData().getMember().getNickname());
                dateTv.setText(response.getData().getMember().getJoinTime());
                orderIdTv.setText(response.getData().getOrderdetail().getOrderid());
                orderDateTv.setText(response.getData().getOrderdetail().getCreateTime());
                adapter.setNewData(list);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

    }

    private void payBack(final String orderId) {
        NetTool.getPayBack(SpUtil.getInt(Constant.STORE_ID), orderId, new GsonResponseHandler<BaseBean<String>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<String> response) {
                if (response.getData().isEmpty()) {
                    ToastUtils.showLong(response.getMsg());
                } else {
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                    ToastUtils.showLong("退单成功！");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }
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
