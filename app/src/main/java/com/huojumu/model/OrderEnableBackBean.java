package com.huojumu.model;

import java.util.List;

public class OrderEnableBackBean {

    private List<PayInfoBean> payInfoList;
    private List<OrderBackBean> orders;

    public List<PayInfoBean> getPayInfoList() {
        return payInfoList;
    }

    public void setPayInfoList(List<PayInfoBean> payInfoList) {
        this.payInfoList = payInfoList;
    }

    public List<OrderBackBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderBackBean> orders) {
        this.orders = orders;
    }
}
