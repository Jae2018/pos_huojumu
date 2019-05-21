package com.huojumu.down;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.OnClick;

public class DownProgressDialog extends BaseDialog {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.update_tv)
    TextView updateTv;
    @BindView(R.id.update_btn)
    Button update_btn;
    @BindView(R.id.cancel_iv)
    ImageView cancelBtn;

    private String apkPath;

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
                apkPath = path;
                installApk(path);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void fail(int code, String message) {
                ToastUtils.showLong("网络连接出错");
            }

            @Override
            public void loadFail(String message) {
                if (message.equals("timeout")) {
                    ToastUtils.showLong("网络超时，请检查网络状态");
                } else {
                    ToastUtils.showLong("已取消下载更新");
                }
                cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DownloadUtil.cancelRequest();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                updateTv.setText("下载完毕，点击安装黄新版本");
                update_btn.setVisibility(View.VISIBLE);
                cancelBtn.setVisibility(View.VISIBLE);
            }
        }
    };

    @OnClick(R.id.update_btn)
    void install0() {
        if (apkPath != null) {
            installApk(apkPath);
        }
    }

    @OnClick(R.id.cancel_iv)
    void cancelIv() {
        cancel();
    }

    private void installApk(String path) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param apkPath 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    private boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.e("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
            result = false;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }

}
