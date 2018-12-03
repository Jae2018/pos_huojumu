package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 扫码支付类型
 */
public class OrderBack {

    /**
     * orderId : xxxxx
     * orderNo : xxxxx
     * pickUpCode : testtest
     * totalPrice : 10.4
     * chinaUnionQrcode : https://qr.95516.com/00010001/62112876966626632636026794415061
     * wxPayQrcode : wxpay://xxxxxxxxxx
     * aliPayQrcode : https://xxxxxxxxx
     */

    private String orderId;
    private String orderNo;
    private String pickUpCode;
    private double totalPrice;
    private String chinaUnionQrcode;
    private String wxPayQrcode;
    private String aliPayQrcode;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPickUpCode() {
        return pickUpCode;
    }

    public void setPickUpCode(String pickUpCode) {
        this.pickUpCode = pickUpCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getChinaUnionQrcode() {
        return chinaUnionQrcode;
    }

    public void setChinaUnionQrcode(String chinaUnionQrcode) {
        this.chinaUnionQrcode = chinaUnionQrcode;
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
}
