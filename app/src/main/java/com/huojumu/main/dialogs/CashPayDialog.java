package com.huojumu.main.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;

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
    TextView change;

    private DialogInterface anInterface;

    private double cost;//订单金额
    private int cash = 0;//收取金额

    public CashPayDialog(@NonNull Context context, double cost, DialogInterface anInterface) {
        super(context);
        this.cost = cost;
        this.anInterface = anInterface;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_cash_pay;
    }

    @Override
    public void initView() {
        earn1.setText(String.valueOf(cost));
    }

    @OnClick({R.id.dialog_cash_pay_ten, R.id.dialog_cash_pay_twenty, R.id.dialog_cash_pay_fifty, R.id.dialog_cash_pay_hundred})
    void OnCashClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_cash_pay_ten:
                cash = 10;
                break;
            case R.id.dialog_cash_pay_twenty:
                cash = 20;
                break;
            case R.id.dialog_cash_pay_fifty:
                cash = 50;
                break;
            case R.id.dialog_cash_pay_hundred:
                cash = 100;
                break;
        }
        earn2.setText(String.valueOf(cash));
        change.setText(String.valueOf(cash - cost));
    }

    @OnClick(R.id.cash_dialog_cancel)
    void OnCancel() {
        dismiss();
    }

    @OnClick(R.id.cash_dialog_ok)
    void OnOk() {
        anInterface.OnDialogOkClick(cash - cost, "CashPayDialog");
    }

}
