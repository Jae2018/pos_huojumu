package com.huojumu.model;

import java.util.List;

public class ActivesBean {


    /**
     * planType : 1
     * endDate : 2019-01-05 00:00:00
     * validWeek : 8
     * planName : 活动1
     * bargainProIds : [1,2,33,44,55]
     * upToPrice : 13.0
     * offPrice : 12.0
     * compId : 1
     * validTime : 1
     * id : 1
     * upToCount : 1
     * freeCount : 1
     * startDate : 2018-08-20 00:00:00
     * ratio : 90
     */

    private String planType;
    private String endDate;
    private String validWeek;
    private String planName;
    private double upToPrice;
    private double offPrice;
    private int compId;
    private String validTime;
    private int id;
    private int upToCount;
    private int freeCount;
    private String startDate;
    private int ratio;
    private List<Integer> bargainProIds;

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getValidWeek() {
        return validWeek;
    }

    public void setValidWeek(String validWeek) {
        this.validWeek = validWeek;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getUpToPrice() {
        return upToPrice;
    }

    public void setUpToPrice(double upToPrice) {
        this.upToPrice = upToPrice;
    }

    public double getOffPrice() {
        return offPrice;
    }

    public void setOffPrice(double offPrice) {
        this.offPrice = offPrice;
    }

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUpToCount() {
        return upToCount;
    }

    public void setUpToCount(int upToCount) {
        this.upToCount = upToCount;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public List<Integer> getBargainProIds() {
        return bargainProIds;
    }

    public void setBargainProIds(List<Integer> bargainProIds) {
        this.bargainProIds = bargainProIds;
    }
}
