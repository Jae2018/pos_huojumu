package com.huojumu.down;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.huojumu.R;
import com.huojumu.base.BaseDialog;
import com.huojumu.utils.LogUtil;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

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
                install(path);

            }

            @Override
            public void fail(int code, String message) {
                ToastUtils.showLong("网络连接出错");
            }

            @Override
            public void loadFail(String message) {
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
