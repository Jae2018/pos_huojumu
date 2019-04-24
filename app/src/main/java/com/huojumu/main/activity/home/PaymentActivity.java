package com.huojumu.main.activity.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huojumu.R;
import com.huojumu.adapter.MenuRecyclerAdapter;
import com.huojumu.adapter.PaymentActivityAdapter;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.ActivesBean;
import com.huojumu.model.BaseBean;
import com.huojumu.model.BoxPay;
import com.huojumu.model.NoNetPayBack;
import com.huojumu.model.OrderBack;
import com.huojumu.model.OrderInfo;
import com.huojumu.model.PayMenuBean;
import com.huojumu.model.Production;
import com.huojumu.utils.NetTool;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SingleClick;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/11/26
 * Description: qq：494669467，wx：s494669467
 * 结账
 */
public class PaymentActivity extends BaseActivity {

    @BindView(R.id.menu_recycler)
    RecyclerView menuRecycler;
    @BindView(R.id.active_recycler)
    RecyclerView activeRecycler;
    @BindView(R.id.et_input1)
    EditText earnEdit;
    @BindView(R.id.et_input)
    EditText cashPayInput;
    @BindView(R.id.tv_payment_code_msg)
    TextView MsgTv;
    @BindView(R.id.layout_cash)
    LinearLayout layoutCash;
    @BindView(R.id.layout_online)
    LinearLayout layoutOnline;
    @BindView(R.id.layout_vip)
    LinearLayout layoutVip;
    @BindView(R.id.layout_bank)
    LinearLayout layoutBank;
    @BindView(R.id.tv_pay_cash_total)
    TextView cashTotalTv;
    @BindView(R.id.btn_pay_by_code)
    Button btn;
    @BindView(R.id.tv_online_price)
    TextView onlineTv;
    @BindView(R.id.tv_input_error)
    TextView inputErrorTv;

    //支付方式
    private List<PayMenuBean> menuBeans = new ArrayList<>();
    private String[] menuStr = {"现金支付", "微信扫码支付", "支付宝扫码支付", "会员通道", "刷卡支付"};
    //默认现金支付方式
    private String payType = "900";
    //活动集合
    private ArrayList<ActivesBean> activesBeans;
    //
    private ArrayList<Production> productions;
    //订单总价（原价）
    private double origionalPrice;
    //订单计算后的价格
    private double commitPrice = 0;
    //订单商品数量
    private int number = 0;
    //优惠金额
    private double cutPrice = 0;
    //是否开始扫码
    private boolean startScan = false;
    //提示信息
    private AlertDialog dialog;
    //活动类型
    private ActivesBean activesBean;
    //订单数据
    private OrderInfo orderInfo;
    //
    private String orderNo;

    @Override
    protected int setLayout() {
        return R.layout.activity_payment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        productions = getIntent().getParcelableArrayListExtra("proList");
        orderInfo = getIntent().getParcelableExtra("orderInfo");

        activesBeans = getIntent().getParcelableArrayListExtra("actives");

        origionalPrice = getIntent().getDoubleExtra("orderTotal", 0);
        number = getIntent().getIntExtra("totalCount", 0);

        DividerItemDecoration d = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        d.setDrawable(getResources().getDrawable(R.drawable.divider_n, null));
        menuRecycler.setLayoutManager(new LinearLayoutManager(this));
        menuRecycler.addItemDecoration(d);

        activeRecycler.setLayoutManager(new LinearLayoutManager(this));
        activeRecycler.addItemDecoration(d);

        dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage("暂未开通，敬请期待");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        cashPayInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (Integer.parseInt(s.toString().trim()) <= origionalPrice) {
                        inputErrorTv.setVisibility(View.INVISIBLE);
                    } else {
                        inputErrorTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        for (int i = 0; i < menuStr.length; i++) {
            PayMenuBean bean = new PayMenuBean();
            bean.setName(menuStr[i]);
            bean.setSelected(i == 0);
            menuBeans.add(bean);
        }

        if (activesBeans == null) {
            activesBeans = new ArrayList<>();
        }
        ActivesBean bean = new ActivesBean();
        bean.setPlanType("0");
        bean.setPlanName("半价销售");
        activesBeans.add(bean);

        Collections.sort(productions, new Comparator<Production>() {
            @Override
            public int compare(Production o1, Production o2) {
                return (int) (o1.getScalePrice() - o2.getScalePrice());
            }
        });

        //初始默认显示现金支付页面
        cashTotalTv.setText(String.valueOf(origionalPrice));
        final MenuRecyclerAdapter menuRecyclerAdapter = new MenuRecyclerAdapter(menuBeans);
        menuRecyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //选择支付方式
                refreshMenuAdapter(position);
                menuRecyclerAdapter.notifyDataSetChanged();
                //显示支付UI
                switch (position) {
                    case 0:
                        //现金支付页面
                        payType = "900";
                        cashTotalTv.setText(String.valueOf(origionalPrice));
                        break;
                    case 1:
                        //扫码支付页面
                        payType = "010";
                        calculatePrice();
                        onlineTv.setText(String.valueOf(origionalPrice));
                        break;
                    case 2:
                        //扫码支付页面
                        payType = "020";
                        calculatePrice();
                        onlineTv.setText(String.valueOf(origionalPrice));
                        break;
                    case 3:
                        //会员支付页面
                        payType = "0";
                    case 4:
                        //银行卡支付页面
                        dialog.show();
                }
                refreshPayUI(position);
            }
        });
        menuRecycler.setAdapter(menuRecyclerAdapter);

        PaymentActivityAdapter activityAdapter = new PaymentActivityAdapter(activesBeans);
        activityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1500)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //选择活动优惠套餐
                activesBean = activesBeans.get(position);
                calculatePrice();
                cashTotalTv.setText(String.valueOf(commitPrice));
                onlineTv.setText(String.valueOf(commitPrice));
            }
        });
        activeRecycler.setAdapter(activityAdapter);

    }

    /**
     * 计算活动价格
     */
    private void calculatePrice() {
        if (activesBean != null) {
            switch (activesBean.getPlanType()) {
                case "0":
                    commitPrice = origionalPrice / 2;
                    cutPrice = origionalPrice - commitPrice;
                    break;
                case "1":
                    commitPrice = origionalPrice * activesBean.getRatio() / 100;
                    cutPrice = origionalPrice - commitPrice;
                    break;
                case "2":
                    if (origionalPrice >= activesBean.getUpToPrice()) {
                        commitPrice = origionalPrice - activesBean.getOffPrice();
                    } else {
                        commitPrice = origionalPrice;
                    }
                    break;
                case "3":
                    commitPrice = origionalPrice;
                    break;
                case "4":
                    if (number >= activesBean.getUpToCount()) {
                        commitPrice = origionalPrice - productions.get(0).getScalePrice() - productions.get(0).getMateP();
                        cutPrice = productions.get(0).getScalePrice() - productions.get(0).getMateP();
                    } else {
                        commitPrice = origionalPrice;
                    }
                    break;
            }
        } else {
            commitPrice = origionalPrice;
        }
    }

    /**
     * 回退按钮
     */
    @OnClick(R.id.btn_back)
    void goBack() {
        finish();
    }

    /**
     * 扫码支付按钮
     */
    @SingleClick
    @OnClick(R.id.btn_pay_by_code)
    void payByCode() {
        if (startScan) {
            //可以开始扫码
            MsgTv.setVisibility(View.VISIBLE);
            startScan = false;
            btn.setText("取消扫码");
        } else {
            //取消扫码
            authNo = "";
            time = 0;
            MsgTv.setVisibility(View.INVISIBLE);
            startScan = true;
            btn.setText("开始扫码");
        }
    }

    /**
     * 刷新菜单ui
     */
    private void refreshMenuAdapter(int position) {
        for (int i = 0; i < menuBeans.size(); i++) {
            menuBeans.get(i).setSelected(i == position);
        }
    }

    /**
     * 刷新结账方式布局
     */
    private void refreshPayUI(int position) {
        layoutCash.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        layoutOnline.setVisibility((position == 1 || position == 2) ? View.VISIBLE : View.GONE);
        layoutVip.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
        layoutBank.setVisibility(position == 4 ? View.VISIBLE : View.GONE);
    }

    OrderBack orderBack;


    @SingleClick
    @OnClick(R.id.pay_commit)
    void commitOrder() {
        //提交订单结算，主页面刷新UI
        orderInfo.setPayType(payType);
        if (activesBean != null) {
            orderInfo.setDiscountsActivity(activesBean.getPlanType());
            orderInfo.setDiscountsType("2");
        }

        //手动折扣
        int manualDiscount = cashPayInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(cashPayInput.getText().toString());
        if (manualDiscount != 0) {
            orderInfo.setManualDiscount(manualDiscount);
        }

        //特殊人群、一律半价
        if (payType.equals("0")) {
            orderInfo.setManualDiscount((int) (commitPrice == 0 ? (origionalPrice / 2) : (commitPrice / 2)));
        }

        //客户支付的金额
        final double earn = earnEdit.getText().toString().isEmpty() ? 0 : Double.parseDouble(earnEdit.getText().toString());
        if (commitPrice == 0) {
            //没有活动
            if (earn > origionalPrice) {
                //找零，收款 - 总价 + 手动折扣金额
                orderBack.setCharge(earn - origionalPrice + manualDiscount);
            }
        } else {
            //有活动优惠
            if (earn > commitPrice) {
                //找零，收款 - 总价 + 手动折扣金额
                orderBack.setCharge(earn - commitPrice + manualDiscount);
            }
        }

        NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                orderNo = response.getData().getOrderNo();
                orderBack = response.getData();
                orderBack.setPayType(payType);
                orderBack.setCharge(earn - origionalPrice);
                orderBack.setCut(Double.parseDouble(orderBack.getOrigionTotalPrice()) - Double.parseDouble(orderBack.getTotalPrice()));
                if (payType.equals("900")) {
                    //现金
                    EventBus.getDefault().post(orderBack);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                EventBus.getDefault().post(new NoNetPayBack(commitPrice, Double.parseDouble(earnEdit.getText().toString()), cutPrice, "现金支付"));
                finish();
            }
        });
    }

    @SingleClick(1500)
    @OnClick(R.id.pay_cancel)
    void payCancel() {
        finish();
    }

    //扫码回调方法次数，19次获取完
    private int time = 0;
    //扫码内容
    private String authNo;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //扫码监听，系统的软键盘  按下去是 -1, 不管，不拦截
        if (event.getDeviceId() != -1) {
            //按下弹起，识别到弹起的话算一次 有效输入
            //只要是 扫码枪的事件  都要把他消费掉 不然会被editText 显示出
            if (event.getAction() == KeyEvent.ACTION_UP) {
                time++;
                //只要数字，一维码里面没有 字母
                int code = event.getKeyCode();
                if (code >= KeyEvent.KEYCODE_0 && code <= KeyEvent.KEYCODE_9) {
                    authNo += String.valueOf(code - KeyEvent.KEYCODE_0);
                }
                //无订单不走接口
                if (orderInfo != null) {
                    if (time == 19) {
                        payByBox();
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 盒子支付接口
     */
    private void payByBox() {
        time = 0;
        if (orderInfo != null) {
            NetTool.payByBox(orderNo, orderInfo.getPayType(), authNo, new GsonResponseHandler<BaseBean<BoxPay>>() {
                @Override
                public void onSuccess(int statusCode, BaseBean<BoxPay> response) {
                    authNo = "";
                    if (response.getCode().equals("0")) {
                        EventBus.getDefault().post(orderBack);
                    } else {
                        ToastUtils.showLong(response.getMsg());
                    }
                }

                @Override
                public void onFailure(int statusCode, String code, String error_msg) {
                    authNo = "";
                }
            });
        }
    }

}
