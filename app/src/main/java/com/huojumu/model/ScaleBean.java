package com.huojumu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ScaleBean implements Parcelable {

    private int scaleId;
    private String scaName;
    private String unitName;
    private double price;
    private int proId;
    private int unitId;
    private String type;
    private double capacity;
    private double origionPrice;

    public double getOrigionPrice() {
        return origionPrice;
    }

    public void setOrigionPrice(double origionPrice) {
        this.origionPrice = origionPrice;
    }

    public int getScaleId() {
        return scaleId;
    }

    public void setScaleId(int scaleId) {
        this.scaleId = scaleId;
    }

    public String getScaName() {
        return scaName;
    }

    public void setScaName(String scaName) {
        this.scaName = scaName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.scaleId);
        dest.writeString(this.scaName);
        dest.writeString(this.unitName);
        dest.writeDouble(this.price);
        dest.writeInt(this.proId);
        dest.writeInt(this.unitId);
        dest.writeString(this.type);
        dest.writeDouble(this.capacity);
        dest.writeDouble(this.origionPrice);
    }

    public ScaleBean() {
    }

    protected ScaleBean(Parcel in) {
        this.scaleId = in.readInt();
        this.scaName = in.readString();
        this.unitName = in.readString();
        this.price = in.readDouble();
        this.proId = in.readInt();
        this.unitId = in.readInt();
        this.type = in.readString();
        this.capacity = in.readDouble();
        this.origionPrice = in.readDouble();
    }

    public static final Parcelable.Creator<ScaleBean> CREATOR = new Parcelable.Creator<ScaleBean>() {
        @Override
        public ScaleBean createFromParcel(Parcel source) {
            return new ScaleBean(source);
        }

        @Override
        public ScaleBean[] newArray(int size) {
            return new ScaleBean[size];
        }
    };
}
