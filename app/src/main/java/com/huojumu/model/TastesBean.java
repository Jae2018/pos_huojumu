package com.huojumu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TastesBean implements Parcelable {
    /**
     * tasteId : 2
     * tasteName : 默认口味
     * groupId : 1
     * multiple : 1.0
     */

    private int tasteId;
    private String tasteName;
    private int groupId;
    private double multiple;

    public int getTasteId() {
        return tasteId;
    }

    public void setTasteId(int tasteId) {
        this.tasteId = tasteId;
    }

    public String getTasteName() {
        return tasteName;
    }

    public void setTasteName(String tasteName) {
        this.tasteName = tasteName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public double getMultiple() {
        return multiple;
    }

    public void setMultiple(double multiple) {
        this.multiple = multiple;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tasteId);
        dest.writeString(this.tasteName);
        dest.writeInt(this.groupId);
        dest.writeDouble(this.multiple);
    }

    public TastesBean() {
    }

    protected TastesBean(Parcel in) {
        this.tasteId = in.readInt();
        this.tasteName = in.readString();
        this.groupId = in.readInt();
        this.multiple = in.readDouble();
    }

    public static final Parcelable.Creator<TastesBean> CREATOR = new Parcelable.Creator<TastesBean>() {
        @Override
        public TastesBean createFromParcel(Parcel source) {
            return new TastesBean(source);
        }

        @Override
        public TastesBean[] newArray(int size) {
            return new TastesBean[size];
        }
    };
}
