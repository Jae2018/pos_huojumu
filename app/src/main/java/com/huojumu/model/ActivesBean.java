package com.huojumu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ActivesBean implements Parcelable {


    /**
     * 满减、折扣 与 赠送、特价互斥
     * planType : 1    1-折扣;2-满减;3-特价;4-满赠;
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
    //本地添加属性
    private boolean isOpen = true;//是否开启状态、默认开启状态，时间过就关闭

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.planType);
        dest.writeString(this.endDate);
        dest.writeString(this.validWeek);
        dest.writeString(this.planName);
        dest.writeDouble(this.upToPrice);
        dest.writeDouble(this.offPrice);
        dest.writeInt(this.compId);
        dest.writeString(this.validTime);
        dest.writeInt(this.id);
        dest.writeInt(this.upToCount);
        dest.writeInt(this.freeCount);
        dest.writeString(this.startDate);
        dest.writeInt(this.ratio);
        dest.writeList(this.bargainProIds);
        dest.writeByte(this.isOpen ? (byte) 1 : (byte) 0);
    }

    public ActivesBean() {
    }

    protected ActivesBean(Parcel in) {
        this.planType = in.readString();
        this.endDate = in.readString();
        this.validWeek = in.readString();
        this.planName = in.readString();
        this.upToPrice = in.readDouble();
        this.offPrice = in.readDouble();
        this.compId = in.readInt();
        this.validTime = in.readString();
        this.id = in.readInt();
        this.upToCount = in.readInt();
        this.freeCount = in.readInt();
        this.startDate = in.readString();
        this.ratio = in.readInt();
        this.bargainProIds = new ArrayList<Integer>();
        in.readList(this.bargainProIds, Integer.class.getClassLoader());
        this.isOpen = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ActivesBean> CREATOR = new Parcelable.Creator<ActivesBean>() {
        @Override
        public ActivesBean createFromParcel(Parcel source) {
            return new ActivesBean(source);
        }

        @Override
        public ActivesBean[] newArray(int size) {
            return new ActivesBean[size];
        }
    };
}
