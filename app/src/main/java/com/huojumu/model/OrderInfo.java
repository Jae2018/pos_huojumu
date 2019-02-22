package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/16
 * Description: 扫描枪支付上传
 */
public class OrderInfo {


    /**
     * orderID : 12121212122
     * createTime : 2017-01-01 00:00:00
     * shopID : 3
     * ordSource : 2
     * enterpriseID : 34
     * pinpaiID : 33
     * quanIds : [32,45,22]
     * orderType : 2
     * startTime : 2018-11-13 16:00:00
     * endTime : 2018-11-13 16:30:00
     * payType : 900
     * discountsType : 2
     * discountsActivity : 6
     * data : [{"proId":2,"num":3,"proType":"1","tastes":[{"tasteId":1},{"tasteId":2}],"makes":[{"practiceId":1},{"practiceId":2}],"mats":[{"proMatId":1},{"proMatId":2}]}]
     */

    private String orderID;
    private String createTime;
    private int shopID;
    private String ordSource;
    private int enterpriseID;
    private int pinpaiID;
    private String orderType;
    private String startTime;
    private String endTime;
    private String payType;
    private String discountsType;
    private String discountsActivity;
    private List<Integer> quanIds;
    private List<DataBean> data;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(String ordSource) {
        this.ordSource = ordSource;
    }

    public int getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(int enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public int getPinpaiID() {
        return pinpaiID;
    }

    public void setPinpaiID(int pinpaiID) {
        this.pinpaiID = pinpaiID;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public List<Integer> getQuanIds() {
        return quanIds;
    }

    public void setQuanIds(List<Integer> quanIds) {
        this.quanIds = quanIds;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * proId : 2
         * num : 3
         * proType : 1
         * tastes : [{"tasteId":1},{"tasteId":2}]
         * makes : [{"practiceId":1},{"practiceId":2}]
         * mats : [{"proMatId":1},{"proMatId":2}]
         */

        private int proId;
        private int num;
        private String proType;
        private List<TastesBean> tastes;
        private List<MakesBean> makes;
        private List<MatsBean> mats;

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getProType() {
            return proType;
        }

        public void setProType(String proType) {
            this.proType = proType;
        }

        public List<TastesBean> getTastes() {
            return tastes;
        }

        public void setTastes(List<TastesBean> tastes) {
            this.tastes = tastes;
        }

        public List<MakesBean> getMakes() {
            return makes;
        }

        public void setMakes(List<MakesBean> makes) {
            this.makes = makes;
        }

        public List<MatsBean> getMats() {
            return mats;
        }

        public void setMats(List<MatsBean> mats) {
            this.mats = mats;
        }

    }
}
