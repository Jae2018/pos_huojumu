package com.huojumu.model;

public class NetErrorHandler {

    public NetErrorHandler(boolean isConnected) {
        this.isConnected = isConnected;
    }

    private boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

}
