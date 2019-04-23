package com.huojumu.down;

import android.util.Log;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class TrustAllManager  implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType){
        Log.d("TrustAllManager", "checkClientTrusted: ");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        Log.d("TrustAllManager", "checkServerTrusted: ");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
