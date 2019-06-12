package com.huojumu.wedgit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseDialog;

import butterknife.BindView;

public class CustomProgressDialog extends BaseDialog {

    @BindView(R.id.img)
    ImageView spaceshipImage;
    @BindView(R.id.tipTextView)
    TextView tipTextView;

    public CustomProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int setLayout() {
        return R.layout.loading_dialog;
    }

    @Override
    public void initView() {
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                getContext(), R.anim.load_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setTipTextView(String msg) {
        this.tipTextView.setText(msg);
    }

//    /**
//     * 得到自定义的progressDialog
//     *
//     */
//    public static Dialog createLoadingDialog(Context context, String msg) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
//        LinearLayout layout = v.findViewById(R.id.dialog_view);// 加载布局
//        // main.xml中的ImageView
//        ImageView spaceshipImage =  v.findViewById(R.id.img);
//        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
//
//        tipTextView.setText(msg);// 设置加载信息
//
//        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
//
//        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
//        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
//        return loadingDialog;
//
//    }

}
