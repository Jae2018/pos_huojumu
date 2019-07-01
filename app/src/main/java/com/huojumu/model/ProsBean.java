package com.huojumu.model;


import java.util.List;

public class ProsBean {
    /**
     * mats : [{"proMatId":2187,"ingredientPrice":1,"matName":"芦荟粒"}]
     * tastes : [{"tasteId":1,"tasteName":"半糖"}]
     * sumPrice : 15.00
     * price : 14
     * proId : 133
     * proCount : 1
     * proName : 樱花草莓奶
     * cups : [{"cupId":125,"proId":133,"proName":"樱花草莓奶"}]
     * orderDeailId : 91
     */

    private String sumPrice;
    private double price;
    private int proId;
    private int proCount;
    private String proName;
    private String proNameEn;
    private int orderDeailId;
    private String matStr;
    private String scaleName;
    private List<MatsBean> mats;
    private List<TastesBean> tastes;
    private List<CupsBean> cups;

    public String getProNameEn() {
        return proNameEn;
    }

    public void setProNameEn(String proNameEn) {
        this.proNameEn = proNameEn;
    }

    public String getMatStr() {
        return matStr;
    }

    public void setMatStr(String matStr) {
        this.matStr = matStr;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getProCount() {
        return proCount;
    }

    public void setProCount(int proCount) {
        this.proCount = proCount;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getOrderDeailId() {
        return orderDeailId;
    }

    public void setOrderDeailId(int orderDeailId) {
        this.orderDeailId = orderDeailId;
    }

    public List<MatsBean> getMats() {
        return mats;
    }

    public void setMats(List<MatsBean> mats) {
        this.mats = mats;
    }

    public List<TastesBean> getTastes() {
        return tastes;
    }

    public void setTastes(List<TastesBean> tastes) {
        this.tastes = tastes;
    }

    public List<CupsBean> getCups() {
        return cups;
    }

    public void setCups(List<CupsBean> cups) {
        this.cups = cups;
    }

}
