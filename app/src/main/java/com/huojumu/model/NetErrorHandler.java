package com.huojumu.model;

public class NetErrorHandler {

    public NetErrorHandler(boolean isConnected) {
        this.isConnected = isConnected;
    }

    private boolean isConnected;
    private boolean fromLogin = false;

    public boolean isFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(boolean fromLogin) {
        this.fromLogin = fromLogin;
    }

    public NetErrorHandler(boolean isConnected, boolean fromLogin) {
        this.isConnected = isConnected;
        this.fromLogin = fromLogin;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

}
