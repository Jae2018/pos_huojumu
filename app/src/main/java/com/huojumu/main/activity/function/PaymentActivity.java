package com.huojumu.main.activity.function;

import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.model.BaseBean;
import com.huojumu.model.OrderBack;
import com.huojumu.utils.KeyboardUtil;
import com.huojumu.utils.NetTool;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/11/26
 * Description: qq：494669467，wx：s494669467
 * 结账
 */
public class PaymentActivity extends BaseActivity {

    @BindView(R.id.tv_total_cast)
    TextView totalCast;
    @BindView(R.id.et_vip_no)
    EditText vipNo;
    @BindView(R.id.et_key_bord)
    EditText key;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    @BindView(R.id.btn_pay_cash)
    Button cashBtn;
    @BindView(R.id.btn_pay_qrcode)
    Button qrBtn;
    @BindView(R.id.btn_pay_gun)
    Button gunBtn;


    private double payTotal = 0;
    private String json;

    @Override
    protected int setLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {
        KeyboardUtil keyboardUtil = new KeyboardUtil(this);
        keyboardUtil.setEd(key);
    }

    @Override
    protected void initData() {
        json = getIntent().getStringExtra("orderJson");
        double payTotal = getIntent().getDoubleExtra("orderTotal",0.0);
        totalCast.setText(String.format(Locale.CHINA, "¥ %.2f", payTotal));
    }

    @OnClick(R.id.btn_pay_cash)
    void payByCash() {
        if (key.getText().length() == 0) {
            ToastUtils.showLong("请输入金额");
            return;
        }
        if (cashBtn.isSelected()) {
            cashBtn.setSelected(false);
            cashBtn.setBackgroundColor(getResources().getColor(R.color.btn_den));
            cashBtn.setTextColor(Color.BLACK);
        } else {
            cashBtn.setSelected(true);
            qrBtn.setSelected(false);
            gunBtn.setSelected(false);
            cashBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            cashBtn.setTextColor(Color.WHITE);
        }
    }

    @OnClick(R.id.btn_pay_qrcode)
    void payByQr() {
        if (key.getText().length() == 0) {
            ToastUtils.showLong("请输入金额");
            return;
        }
        if (qrBtn.isSelected()) {
            qrBtn.setSelected(false);
            qrBtn.setBackgroundColor(getResources().getColor(R.color.btn_den));
            qrBtn.setTextColor(Color.BLACK);
        } else {
            qrBtn.setSelected(true);
            cashBtn.setSelected(false);
            gunBtn.setSelected(false);
            qrBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            qrBtn.setTextColor(Color.WHITE);
        }
    }

//    @OnClick(R.id.btn_pay_vip)
//    void payByVip() {
//        if (vipNo.getText().length() == 0) {
//            return;
//        }
//        searchVip();
//    }

    @OnClick(R.id.btn_pay_gun)
    void payByGun() {
        if (key.getText().length() == 0) {
            ToastUtils.showLong("请输入金额");
            return;
        }
        if (gunBtn.isSelected()) {
            gunBtn.setSelected(false);
            gunBtn.setBackgroundColor(getResources().getColor(R.color.btn_den));
            gunBtn.setTextColor(Color.BLACK);
        } else {
            gunBtn.setSelected(true);
            cashBtn.setSelected(false);
            qrBtn.setSelected(false);
            gunBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            gunBtn.setTextColor(Color.WHITE);
        }
    }

    @OnClick(R.id.btn_cancel)
    void cancel() {
        finish();
    }

    @OnClick(R.id.btn_commit)
    void commit() {
        payOnline();
    }

//    private void searchVip() {
//
//    }

    private void payOnline() {
        NetTool.postOrder(json, new GsonResponseHandler<BaseBean<OrderBack>>() {
            @Override
            public void onSuccess(int statusCode, BaseBean<OrderBack> response) {
                EventBus.getDefault().post(response);
            }

            @Override
            public void onFailure(int statusCode,String code, String error_msg) {

            }
        });
    }
}
