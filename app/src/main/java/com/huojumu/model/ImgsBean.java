package com.huojumu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImgsBean implements Parcelable {

    /**
     * path : https://www.goodb2b.cn/bootstrap_file/9eae1aaa34901582a7beb91388c1d6bf.jpg
     * sourceType : 1
     */

    private String path;
    private String sourceType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.sourceType);
    }

    public ImgsBean() {
    }

    protected ImgsBean(Parcel in) {
        this.path = in.readString();
        this.sourceType = in.readString();
    }

    public static final Parcelable.Creator<ImgsBean> CREATOR = new Parcelable.Creator<ImgsBean>() {
        @Override
        public ImgsBean createFromParcel(Parcel source) {
            return new ImgsBean(source);
        }

        @Override
        public ImgsBean[] newArray(int size) {
            return new ImgsBean[size];
        }
    };
}
