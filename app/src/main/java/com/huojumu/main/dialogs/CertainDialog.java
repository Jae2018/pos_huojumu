package com.huojumu.main.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/9/29
 * Description:
 */
public class CertainDialog extends BaseDialog {

    @BindView(R.id.tv_title_certain_dialog)
    TextView title;
    @BindView(R.id.tv_content_certain_dialog)
    TextView content;

    private DialogInterface anInterface;
    private String titleStr, conStr;

    public CertainDialog(@NonNull Context context, DialogInterface anInterface,
                         String titleStr, String conStr) {
        super(context);
        this.anInterface = anInterface;
        this.titleStr = titleStr;
        this.conStr = conStr;
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_for_certain;
    }

    @Override
    public void initView() {
        title.setText(titleStr);
        content.setText(conStr);
    }

    public void setText(String titleStr, String conStr) {
        title.setText(titleStr);
        content.setText(conStr);
    }

    @OnClick(R.id.btn_cancel_certain_dialog)
    void Cancel() {
        cancel();
    }

    @OnClick(R.id.btn_ok_certain_dialog)
    void OK() {
        if (anInterface != null) {
            anInterface.OnDialogOkClick(0, 0, 0, 0, "CertainDialog");
        } else {
            cancel();
        }
    }

}
