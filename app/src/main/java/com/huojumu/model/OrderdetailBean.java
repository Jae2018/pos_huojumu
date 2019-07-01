package com.huojumu.model;


import java.util.List;

public class OrderdetailBean {
    /**
     * pros : [{"mats":[{"proMatId":2187,"ingredientPrice":1,"matName":"芦荟粒"}],"tastes":[{"tasteId":1,"tasteName":"半糖"}],"sumPrice":"15.00","price":14,"proId":133,"proCount":1,"proName":"樱花草莓奶","cups":[{"cupId":125,"proId":133,"proName":"樱花草莓奶"}],"orderDeailId":91}]
     * ordNo : DDMDCN51000100010004201904030060
     * payType : 900
     * orderid : 02bff9c685484866b0a885f88f7570ea
     * totalPrice : 15
     * createTime : 2019-04-03 17:58:02
     * ordSource : 3
     */

    private String ordNo;
    private String payType;
    private String orderid;
    private double totalPrice;
    private String createTime;
    private String ordSource;
    private List<ProsBean> pros;

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(String ordSource) {
        this.ordSource = ordSource;
    }

    public List<ProsBean> getPros() {
        return pros;
    }

    public void setPros(List<ProsBean> pros) {
        this.pros = pros;
    }

}
