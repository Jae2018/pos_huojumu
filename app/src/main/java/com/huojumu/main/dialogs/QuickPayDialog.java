package com.huojumu.main.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author : Jie
 * Date: 2018/10/10
 * Description:
 */
public class QuickPayDialog extends BaseDialog {

    @BindView(R.id.tv_quick_type_cash)
    TextView tv_quick_type_cash;
    @BindView(R.id.tv_quick_type_ali)
    TextView tv_quick_type_ali;
    @BindView(R.id.tv_quick_type_wx)
    TextView tv_quick_type_wx;

    private int type = 1;
    private DialogInterface anInterface;

    public QuickPayDialog(@NonNull Context context, DialogInterface anInterface) {
        super(context);
        this.anInterface = anInterface;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_quick_pay;
    }

    @Override
    public void initView() {
        tv_quick_type_cash.setSelected(true);
    }

    @OnClick({R.id.tv_quick_type_cash, R.id.tv_quick_type_ali, R.id.tv_quick_type_wx})
    void OnTypeClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quick_type_cash:
                type = 1;
                tv_quick_type_cash.setSelected(true);
                tv_quick_type_ali.setSelected(false);
                tv_quick_type_wx.setSelected(false);
                break;
            case R.id.tv_quick_type_ali:
                tv_quick_type_cash.setSelected(false);
                tv_quick_type_ali.setSelected(true);
                tv_quick_type_wx.setSelected(false);
                type = 2;
                break;
            case R.id.tv_quick_type_wx:
                tv_quick_type_cash.setSelected(false);
                tv_quick_type_ali.setSelected(false);
                tv_quick_type_wx.setSelected(true);
                type = 3;
                break;
        }
    }

    @OnClick(R.id.quick_dialog_ok)
    void OnOk() {
        anInterface.OnDialogOkClick(type, 0,0,0,"QuickPayDialog");
        cancel();
    }

    @OnClick(R.id.quick_dialog_cancel)
    void OnCancel() {
        cancel();
    }

}
