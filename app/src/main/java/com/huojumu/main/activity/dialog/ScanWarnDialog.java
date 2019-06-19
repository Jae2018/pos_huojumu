package com.huojumu.main.activity.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.listeners.DialogInterface;
import com.huojumu.utils.SingleClick;

import butterknife.BindView;
import butterknife.OnClick;

public class ScanWarnDialog extends BaseDialog {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;

    private DialogInterface dialogInterface;

    public ScanWarnDialog(@NonNull Context context, DialogInterface dialogInterface) {
        super(context);
        this.dialogInterface = dialogInterface;
    }

    @Override
    public int setLayout() {
        return R.layout.scan_warn_dialog;
    }

    @Override
    public void initView() {
        setCancelable(false);
    }

    public void setTextView(String s) {
        textView.setText(s);
    }

    @SingleClick
    @OnClick(R.id.button)
    void click() {
        dialogInterface.OnUsbCallBack("1");
    }

    @SingleClick
    @OnClick(R.id.imageView2)
    void close() {
        cancel();
    }

}
