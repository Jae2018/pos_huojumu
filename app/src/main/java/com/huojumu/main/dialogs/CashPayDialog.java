package com.huojumu.main.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.listeners.DialogInterface;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/10/11
 * Description:
 */
public class CashPayDialog extends BaseDialog {

    @BindView(R.id.tv_cash_pay_earn1)
    TextView earn1;//应收金额
    @BindView(R.id.tv_cash_pay_earn2)
    EditText earn2;//实收金额
    @BindView(R.id.dialog_cash_pay_change)
    TextView change;//找零
    @BindView(R.id.text_error)
    TextView errorTv;
    @BindView(R.id.cash_dialog_ok)
    Button okBtn;

    private DialogInterface anInterface;

    private double totalPrice = 0;//订单金额
    private double earnMoney = 0;//收取金额

    public CashPayDialog(@NonNull Context context, DialogInterface anInterface) {
        super(context);
        this.anInterface = anInterface;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_cash_pay;
    }

    @Override
    public void initView() {
        earn1.setText(String.valueOf(totalPrice));
        earn2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    earnMoney = Double.valueOf(s.toString().trim());
                }
            }
        });
    }

    @OnClick({R.id.dialog_cash_pay_ten, R.id.dialog_cash_pay_twenty, R.id.dialog_cash_pay_fifty, R.id.dialog_cash_pay_hundred})
    void OnCashClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_cash_pay_ten:
                earnMoney = 10;
                break;
            case R.id.dialog_cash_pay_twenty:
                earnMoney = 20;
                break;
            case R.id.dialog_cash_pay_fifty:
                earnMoney = 50;
                break;
            case R.id.dialog_cash_pay_hundred:
                earnMoney = 100;
                break;
        }
        earn2.setText(String.valueOf(earnMoney));
        change.setText(String.valueOf(earnMoney - totalPrice));
    }

    @OnClick(R.id.cash_dialog_cancel)
    void OnCancel() {
        clear();
        dismiss();
    }

    @OnClick(R.id.cash_dialog_ok)
    void OnOk() {
        if (earnMoney == 0) {
            earnMoney = totalPrice;
        } else {
            if (earnMoney < totalPrice) {
                ToastUtils.showLong("输入金额不能小于商品实际价格");
                return;
            }
        }
        anInterface.OnDialogOkClick(0, earnMoney, totalPrice, earnMoney - totalPrice, "CashPayDialog");
        clear();
    }

    private void clear() {
        earn1.setText("");
        earn2.setText("");
        change.setText("");
        earnMoney = 0;
    }

    public void setTotalMoney(double totalPrice) {
        this.totalPrice = totalPrice;
        earn1.setText(String.valueOf(totalPrice));
    }

}
