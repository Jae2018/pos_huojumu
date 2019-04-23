package com.huojumu.down;


import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

public class DownloadResponseBody extends ResponseBody {

    private Response originalResponse;
    private DownloadListener downloadListener;

    public DownloadResponseBody(Response originalResponse, DownloadListener downloadListener) {
        this.originalResponse = originalResponse;
        this.downloadListener = downloadListener;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            private long bytesReaded = 0;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                if (downloadListener != null) {
                    downloadListener.loading((int) (bytesReaded / (1024)));
                }
                return bytesRead;
            }
        });
    }
}
