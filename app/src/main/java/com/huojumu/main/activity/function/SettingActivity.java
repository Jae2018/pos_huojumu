package com.huojumu.main.activity.function;

import android.os.Environment;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;
import com.huojumu.utils.UpdateTool;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Jie
 * Date: 2018/11/8
 * Description:
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.switch_pos)
    Switch aSwitch;
    @BindView(R.id.switch_receipt)
    Switch rSwitch;
    @BindView(R.id.tv_app_version)
    TextView versionTv;

//    private DownProgressDialog downProgressDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        aSwitch.setChecked(false);
        aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //控制开关字体颜色
                if (b) {
                    //58mm
                    SpUtil.save(Constant.PRINT_WIDTH, true);
                    aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_true);
                } else {
                    //80mm
                    SpUtil.save(Constant.PRINT_WIDTH, false);
                    aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_false);
                }
            }
        });

        //小票语言种类
        rSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //控制开关字体颜色
                if (b) {
                    //英语
                    SpUtil.save(Constant.PRINT_LANG, true);
                    aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_true);
                } else {
                    //汉语
                    SpUtil.save(Constant.PRINT_LANG, false);
                    aSwitch.setSwitchTextAppearance(SettingActivity.this, R.style.s_false);
                }
            }
        });

        versionTv.setText(UpdateTool.getLocalVersionName(SettingActivity.this));
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_clear_cache)
    void clearCache() {
        clearAllCache();
    }

    @OnClick(R.id.tv_logout)
    void logout() {
        finish();
    }

    private void clearAllCache() {
        deleteDir(getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(getExternalCacheDir());
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return true;
    }

//    @OnClick(R.id.tv_update)
//    void update() {
//        NetTool.updateApk(new GsonResponseHandler<UpdateBean>() {
//            @Override
//            public void onSuccess(int statusCode, UpdateBean response) {
//                if (!UpdateTool.getLocalVersionName(SettingActivity.this).equals(response.getVersionNum())) {
//                    MyOkHttp.mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            downProgressDialog = new DownProgressDialog(SettingActivity.this);
//                            downProgressDialog.setCancelable(false);
//                            downProgressDialog.show();
//                        }
//                    }, 500);
//                } else {
//                    ToastUtils.showLong("已是最新版本");
//                }
//            }
//        });
//
//    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (downProgressDialog != null) {
//            downProgressDialog.cancel();
//            downProgressDialog = null;
//        }
    }

}
