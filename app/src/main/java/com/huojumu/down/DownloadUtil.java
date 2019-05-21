package com.huojumu.down;

import android.support.annotation.NonNull;

import com.huojumu.utils.Constant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadUtil {

    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;
    private static Call call;

    public static DownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .build();
    }

    static void cancelRequest(){
        call.cancel();
    }

    /**
     * @param listener 下载监听
     */
    void download(final DownloadListener listener) {
        Request request = new Request.Builder().url("https://www.goodb2b.cn/filever/filemanager/downloadByPro?projectName=pos").build();
        TrustAllManager manager = new TrustAllManager();
        // 重写ResponseBody监听请求
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new DownloadResponseBody(originalResponse, listener))
                        .build();
            }
        };

        okHttpClient.newBuilder().addNetworkInterceptor(interceptor)
                .sslSocketFactory(createTrustAllSSLFactory(manager), manager);

        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 下载失败
                listener.fail(1, "网络错误");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.body() != null) {
                    long total = response.body().contentLength();

                    listener.start(total);

                    byte[] buff = new byte[2048];
                    int len;

                    InputStream is = null;
                    RandomAccessFile randomAccessFile;
                    BufferedInputStream bis = null;

                    try {
                        is = response.body().byteStream();
                        File file = getFile();
                        bis = new BufferedInputStream(is);
                        randomAccessFile = new RandomAccessFile(file, "rwd");
                        randomAccessFile.seek(0);

                        long sum = 0;
                        while ((len = bis.read(buff)) != -1) {
                            randomAccessFile.write(buff, 0, len);
                            sum += len;
                            // 下载中
                            listener.loading((int) sum);
                        }
                        // 下载完成
                        listener.complete(file.getAbsoluteFile().toString());
                    } catch (Exception e) {
                        listener.loadFail(e.getLocalizedMessage());
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                            if (bis != null)
                                bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private File getFile() {
        File file = new File(Constant.APK_PATH);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }
}
