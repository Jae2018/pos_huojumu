package com.huojumu.model;

public class NoNetPayBack {

    private double totalPrice;
    private double charge;
    private double cut;
    private String type;

    public NoNetPayBack(double totalPrice, double charge, double cut, String type) {
        this.totalPrice = totalPrice;
        this.charge = charge;
        this.cut = cut;
        this.type = type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public double getCut() {
        return cut;
    }

    public void setCut(double cut) {
        this.cut = cut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
