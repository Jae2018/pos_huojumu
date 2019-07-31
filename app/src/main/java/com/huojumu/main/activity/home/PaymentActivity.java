package com.huojumu.main.activity.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
 * Description: 结账
 */
public class PaymentActivity extends BaseActivity {

    @BindView(R.id.menu_recycler)
    RecyclerView menuRecycler;
    @BindView(R.id.active_recycler)
    RecyclerView activeRecycler;
    @BindView(R.id.et_input1)
    TextView earnEdit;
    @BindView(R.id.et_input)
    TextView cashPayInput;
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
    @BindView(R.id.tv_input_error1)
    TextView inputErrorTv1;
    @BindView(R.id.tv_input_error)
    TextView inputErrorTv;
    @BindView(R.id.pay_commit)
    Button commitBtn;

    //支付方式
    private List<PayMenuBean> menuBeans = new ArrayList<>();
    private String[] menuStr = {"现金支付", "微信扫码支付", "支付宝扫码支付", "会员通道", "刷卡支付"};
    //默认现金支付方式
    private String payType = "900";
    //活动集合
    private ArrayList<ActivesBean> activesBeans;
    //订单列表
    private ArrayList<Production> productions;
    //订单总价（原价）
    private double origionalPrice = 0;
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
    //订单号
    private String orderNo;
    //实收金额
    private double ssPrice = 0;
    //手动折扣金额
    private double manualDiscount = 0;
    //找零
    private double charge = 0;
    //半价
    private boolean isHalf = false;
    //服务器回馈订单信息
    private OrderBack orderBack;
    //哪个输入框
    private boolean inputNo = true;
    //数字键盘输入
    private String cNumber = "", gNumber = "";
    private double pPrice = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {
        productions = getIntent().getParcelableArrayListExtra("proList");
        orderInfo = getIntent().getParcelableExtra("orderInfo");
        pPrice = getIntent().getDoubleExtra("pPrice", 0);
        Log.e("pay", "pPrice: " + pPrice);

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

        earnEdit.setText(String.valueOf(origionalPrice));
        cNumber = String.valueOf(origionalPrice);
        cashPayInput.setText(gNumber);
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
                        //微信扫码支付页面
                        payType = "010";
                        calculatePrice();
                        onlineTv.setText(String.valueOf(origionalPrice));
                        commitOrder();
                        break;
                    case 2:
                        //支付宝扫码支付页面
                        payType = "020";
                        calculatePrice();
                        onlineTv.setText(String.valueOf(origionalPrice));
                        commitOrder();
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
                onlineTv.setText(String.valueOf(commitPrice));
            }
        });
        activeRecycler.setAdapter(activityAdapter);

    }

    /**
     * 是否实收输入
     */
    @OnClick(R.id.earn_rel)
    void onEarnInput() {
        inputNo = true;
        earnEdit.setBackgroundColor(Color.WHITE);
        cashPayInput.setBackgroundColor(Color.TRANSPARENT);
        earnEdit.setTextColor(Color.GRAY);
        cashPayInput.setTextColor(Color.WHITE);
    }

    /**
     * 是否手动减额输入
     */
    @OnClick(R.id.cut_rel)
    void onCutInput() {
        inputNo = false;
        earnEdit.setBackgroundColor(Color.TRANSPARENT);
        cashPayInput.setBackgroundColor(Color.WHITE);
        earnEdit.setTextColor(Color.WHITE);
        cashPayInput.setTextColor(Color.GRAY);
    }


    /**
     * 数字键盘对应的按钮
     */
    @OnClick({R.id.tv_no0, R.id.tv_no1, R.id.tv_no2, R.id.tv_no3, R.id.tv_no4, R.id.tv_no5, R.id.tv_no6, R.id.tv_no7, R.id.tv_no8, R.id.tv_no9, R.id.tv_not, R.id.tv_no_delete})
    void onInputNo(View view) {
        switch (view.getId()) {
            case R.id.tv_no0:
                //如果本来就是0，不能再后面添加0
                if (cNumber.equals("0") || gNumber.equals("0")) {
                    return;
                }
                if (inputNo) {
                    cNumber += "0";
                } else {
                    gNumber += "0";
                }
                break;
            case R.id.tv_no1:
                if (inputNo) {
                    cNumber += "1";
                } else {
                    gNumber += "1";
                }
                break;
            case R.id.tv_no2:
                if (inputNo) {
                    cNumber += "2";
                } else {
                    gNumber += "2";
                }
                break;
            case R.id.tv_no3:
                if (inputNo) {
                    cNumber += "3";
                } else {
                    gNumber += "3";
                }
                break;
            case R.id.tv_no4:
                if (inputNo) {
                    cNumber += "4";
                } else {
                    gNumber += "4";
                }
                break;
            case R.id.tv_no5:
                if (inputNo) {
                    cNumber += "5";
                } else {
                    gNumber += "5";
                }
                break;
            case R.id.tv_no6:
                if (inputNo) {
                    cNumber += "6";
                } else {
                    gNumber += "6";
                }
                break;
            case R.id.tv_no7:
                if (inputNo) {
                    cNumber += "7";
                } else {
                    gNumber += "7";
                }
                break;
            case R.id.tv_no8:
                if (inputNo) {
                    cNumber += "8";
                } else {
                    gNumber += "8";
                }
                break;
            case R.id.tv_no9:
                if (inputNo) {
                    cNumber += "9";
                } else {
                    gNumber += "9";
                }
                break;
            case R.id.tv_not:
                if (inputNo) {
                    if (!cNumber.contains(".")) {
                        cNumber += ".";
                    }
                } else {
                    if (!gNumber.contains(".")) {
                        gNumber += ".";
                    }
                }
                break;
            case R.id.tv_no_delete:
                if (inputNo) {
                    cNumber = "";
                } else {
                    gNumber = "";
                }
                break;
        }

        double d1 = Double.parseDouble(cNumber.equals("") ? "0" : cNumber);
        double d = (origionalPrice - d1) > 0 ? (origionalPrice - d1) : 0;
        earnEdit.setText(cNumber);
        if (isHalf && !inputNo) {
            ToastUtils.showLong("半价与手动折扣不能同时进行");
        } else {
            cashPayInput.setText(String.valueOf(d));
        }
    }

    /**
     * 计算活动价格
     */
    private void calculatePrice() {
        if (activesBean != null) {
            switch (activesBean.getPlanType()) {
                case "0":
                    isHalf = true;
                    commitPrice = origionalPrice - pPrice / 2;
                    cutPrice = pPrice / 2;
                    earnEdit.setText(String.valueOf(commitPrice));
                    cashPayInput.setText(String.valueOf(cutPrice));
                    break;
                case "1":
                    isHalf = false;
                    commitPrice = origionalPrice * activesBean.getRatio() / 100;
                    cutPrice = origionalPrice - commitPrice;
                    break;
                case "2":
                    isHalf = false;
                    if (origionalPrice >= activesBean.getUpToPrice()) {
                        commitPrice = origionalPrice - activesBean.getOffPrice();
                    } else {
                        commitPrice = origionalPrice;
                    }
                    break;
                case "3":
                    isHalf = false;
                    commitPrice = origionalPrice;
                    break;
                case "4":
                    isHalf = false;
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
        cNumber = String.valueOf(commitPrice);
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

    @SingleClick
    @OnClick(R.id.pay_commit)
    void commitOrder() {
        //现金支付方式
        if (payType.equals("900")) {
            try {
                ssPrice = earnEdit.getText().toString().isEmpty() ? 0 : Double.parseDouble(earnEdit.getText().toString());
            } catch (Exception e) {
                ToastUtils.showLong("输入有误，请重新输入");
                Log.e("okhttp3", "error");
            }
        }

        //提交订单结算，主页面刷新UI
        orderInfo.setPayType(payType);
        if (activesBean != null) {
            orderInfo.setDiscountsActivity(activesBean.getPlanType());
            orderInfo.setDiscountsType("2");
            //特殊人群、一律半价，不包括加料等额外的
            if (isHalf) {
                orderInfo.setManualDiscount(pPrice / 2);
                manualDiscount = pPrice / 2;
            } else {
                //手动折扣
                manualDiscount = cashPayInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(cashPayInput.getText().toString());
                orderInfo.setManualDiscount(manualDiscount);
            }
        } else {
            //手动折扣
            manualDiscount = cashPayInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(cashPayInput.getText().toString());
            orderInfo.setManualDiscount(manualDiscount);
        }

        //客户支付的金额
        progressDialog.show();

        NetTool.postOrder(PrinterUtil.toJson(orderInfo), new GsonResponseHandler<BaseBean<OrderBack>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                orderNo = response.getData().getOrderNo();
                orderBack = response.getData();
                orderBack.setPayType(payType);
                if (activesBean == null) {
                    //没有活动
                    if ((ssPrice + manualDiscount) >= origionalPrice) {
                        //找零，收款 - 总价 + 手动折扣金额
                        charge = ssPrice - origionalPrice;
                    }
                } else {
                    //有活动优惠
                    if ((ssPrice + manualDiscount) >= commitPrice) {
                        //找零，收款 - 总价 + 手动折扣金额
                        charge = ssPrice - commitPrice;
                    }
                }
                orderBack.setCharge(charge);
                orderBack.setTotal(ssPrice);
                orderBack.setCut(manualDiscount);
                String creatTime = response.getData().getCreatTime();
                String origionTotalPrice = String.valueOf(origionalPrice);
                String totalPrice = response.getData().getTotalPrice();

                progressDialog.dismiss();
                if (payType.equals("900")) {
                    //现金
                    EventBus.getDefault().post(new OrderBack(orderNo, payType, charge, ssPrice, manualDiscount, creatTime, origionTotalPrice, totalPrice));
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, String code, String error_msg) {
                progressDialog.dismiss();
                if (payType.equals("900")) {
                    EventBus.getDefault().post(new NoNetPayBack(commitPrice, ssPrice, cutPrice, "现金支付"));
                    finish();
                }
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
    private String authNo = "";

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
                        finish();
                    } else {
                        ToastUtils.showLong(response.getMsg());
                    }
                }

                @Override
                public void onFailure(int statusCode, String code, String error_msg) {
                    authNo = "";
                    ToastUtils.showLong(error_msg);
                }
            });
        }
    }

}
