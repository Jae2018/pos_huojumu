package com.huojumu.model;

public class EventHandler {

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
