package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 扫码支付类型
 */
public class OrderBack {


    /**
     * pickUpCode : 68160535
     * orderNo : DDMDCN11000100010001201901100018
     * orderId : 1901101008
     * totalPrice : 0.00
     * wxPayQrcode : null
     * aliPayQrcode : null
     * chinaUnionQrcode :
     * origionTotalPrice : 54.00
     */

    private String pickUpCode;
    private String orderNo;
    private String orderId;
    private String totalPrice;
    private String wxPayQrcode;
    private String aliPayQrcode;
    private String chinaUnionQrcode;
    private String origionTotalPrice;
    private String creatTime;
    //本地添加
    private String payType;
    //找零
    private double charge;
    //实收
    private double total;
    //减额
    private double cut;

    public OrderBack(String orderNo, String payType, double charge, double total, double cut, String creatTime, String origionTotalPrice, String totalPrice) {
        this.orderNo = orderNo;
        this.payType = payType;
        this.charge = charge;
        this.total = total;
        this.cut = cut;
        this.creatTime = creatTime;
        this.origionTotalPrice = origionTotalPrice;
        this.totalPrice = totalPrice;
    }

    public double getCut() {
        return cut;
    }

    public void setCut(double cut) {
        this.cut = cut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getPickUpCode() {
        return pickUpCode;
    }

    public void setPickUpCode(String pickUpCode) {
        this.pickUpCode = pickUpCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getWxPayQrcode() {
        return wxPayQrcode;
    }

    public void setWxPayQrcode(String wxPayQrcode) {
        this.wxPayQrcode = wxPayQrcode;
    }

    public String getAliPayQrcode() {
        return aliPayQrcode;
    }

    public void setAliPayQrcode(String aliPayQrcode) {
        this.aliPayQrcode = aliPayQrcode;
    }

    public String getChinaUnionQrcode() {
        return chinaUnionQrcode;
    }

    public void setChinaUnionQrcode(String chinaUnionQrcode) {
        this.chinaUnionQrcode = chinaUnionQrcode;
    }

    public String getOrigionTotalPrice() {
        return origionTotalPrice;
    }

    public void setOrigionTotalPrice(String origionTotalPrice) {
        this.origionTotalPrice = origionTotalPrice;
    }
}
