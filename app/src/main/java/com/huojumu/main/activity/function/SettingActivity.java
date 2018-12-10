package com.huojumu.main.activity.function;

import android.content.Intent;
import android.os.Environment;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.huojumu.R;
import com.huojumu.base.BaseActivity;
import com.huojumu.main.activity.home.HomeActivity;
import com.huojumu.main.activity.login.LoginActivity;
import com.huojumu.utils.Constant;
import com.huojumu.utils.SpUtil;

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
        startActivity(new Intent(this, LoginActivity.class));
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
        return dir.delete();
    }

    @OnClick(R.id.tv_update)
    void update() {
//        NetTool.update(SpUtil.getString(Constant.UUID), new GsonResponseHandler<BaseBean<UpdateBean>>() {
//            @Override
//            public void onSuccess(int statusCode, BaseBean<UpdateBean> response) {
//                if (VersionCodeUtils.getVersionCode(SettingActivity.this) < response.getData().getVersionCode()) {
//                    VersionCodeUtils.downApp();
//                } else {
//                    Toast t = Toast.makeText(SettingActivity.this,"暂无新版本，敬请期待",Toast.LENGTH_LONG);
//                    t.setGravity(Gravity.CENTER, 0, 0);
//                    t.show();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, String error_msg) {
//
//            }
//        });

    }

    @OnClick(R.id.iv_back)
    void back() {
        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        finish();
    }

}
