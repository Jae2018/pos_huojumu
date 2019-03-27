package com.huojumu.model;

public class EventHandler {

    private int userId;
    private String loginName;
    private String mobile;
    private String userName;

    public EventHandler(int userId, String loginName, String mobile, String userName) {
        this.userId = userId;
        this.loginName = loginName;
        this.mobile = mobile;
        this.userName = userName;
    }

    public EventHandler(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
