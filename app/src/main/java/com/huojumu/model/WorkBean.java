package com.huojumu.model;

public class WorkBean {
    public WorkBean(int num, float price) {
        this.num = num;
        this.price = price;
    }

    private int num;
    private float price;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
