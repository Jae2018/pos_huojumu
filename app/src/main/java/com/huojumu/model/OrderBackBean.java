package com.huojumu.model;

import java.util.List;

public class OrderBackBean {

    /**
     * id : 1902182130240
     * ordNo : DDMDCN44000100020001201902190001
     * memberId : 3
     * shopId : 1
     * status : 5
     * createBy : 3
     * createTime : 2019-02-25 10:44:13
     * updateBy : 3
     * updateTime : 2019-02-25 15:40:25
     * remark : null
     * orgionTotalPrice : 0
     * totalPrice : 10
     * payType : 900
     * pickUpCode : 49282368
     * ordSource : 3
     * refundReason : null
     * startTime : null
     * endTime : null
     * orderType : null
     * discountsType : 1
     * discountsActivity : null
     * chargebacke : 0
     * orderDetailEntityList : [{"id":60,"orderId":"1902182130240","proId":4,"proCount":1,"status":"0","createBy":3,"createTime":"2019-02-19 10:44:13","updateBy":null,"updateTime":null,"remark":null,"price":0,"originalPrice":0,"origionUnivalence":0,"univalence":0,"proName":"什么都有奶绿","uuid":"1902182130240","proType":"1"}]
     */

    private String id;
    private String ordNo;
    private int memberId;
    private int shopId;
    private String status;
    private int createBy;
    private String createTime;
    private int updateBy;
    private String updateTime;
    private String remark;
    private int orgionTotalPrice;
    private int totalPrice;
    private String payType;
    private String pickUpCode;
    private String ordSource;
    private String refundReason;
    private String startTime;
    private String endTime;
    private String orderType;
    private String discountsType;
    private String discountsActivity;
    private String chargebacke;
    private List<OrderDetailEntityListBean> orderDetailEntityList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrgionTotalPrice() {
        return orgionTotalPrice;
    }

    public void setOrgionTotalPrice(int orgionTotalPrice) {
        this.orgionTotalPrice = orgionTotalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPickUpCode() {
        return pickUpCode;
    }

    public void setPickUpCode(String pickUpCode) {
        this.pickUpCode = pickUpCode;
    }

    public String getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(String ordSource) {
        this.ordSource = ordSource;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDiscountsType() {
        return discountsType;
    }

    public void setDiscountsType(String discountsType) {
        this.discountsType = discountsType;
    }

    public String getDiscountsActivity() {
        return discountsActivity;
    }

    public void setDiscountsActivity(String discountsActivity) {
        this.discountsActivity = discountsActivity;
    }

    public String getChargebacke() {
        return chargebacke;
    }

    public void setChargebacke(String chargebacke) {
        this.chargebacke = chargebacke;
    }

    public List<OrderDetailEntityListBean> getOrderDetailEntityList() {
        return orderDetailEntityList;
    }

    public void setOrderDetailEntityList(List<OrderDetailEntityListBean> orderDetailEntityList) {
        this.orderDetailEntityList = orderDetailEntityList;
    }

}
