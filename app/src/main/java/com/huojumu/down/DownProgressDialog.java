package com.huojumu.down;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;

import butterknife.BindView;

public class DownProgressDialog extends BaseDialog {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String TAG = "DownProgressDialog";

    public DownProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int setLayout() {
        return R.layout.dialog_download;
    }

    @Override
    public void initView() {
        downloadApk();
    }

    @Permission(PermissionConsts.STORAGE)
    private void downloadApk() {
        DownloadUtil.get().download(new DownloadListener() {
            @Override
            public void start(long max) {
                progressBar.setMax((int) max);
            }

            @Override
            public void loading(int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void complete(String path) {
                installApk(path);
            }

            @Override
            public void fail(int code, String message) {
                ToastUtils.showLong("网络连接出错");
            }

            @Override
            public void loadFail(String message) {
                Log.e(TAG, "loadFail: " + message);
                ToastUtils.showLong("下载失败，请稍后再试");
                cancel();
            }
        });
    }


    private void installApk(String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        getContext().startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
