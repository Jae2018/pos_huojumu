package com.huojumu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MatsBean implements Parcelable {

    /**
     * proMatId : 3285
     * dosage : 0.0
     * ingredientDosage : 1.0
     * ingredientPrice : 2.0
     * addIndex : 1
     * matNo : YL-0076
     * orgId : 76
     * matName : 苏打水
     */

    private int proMatId;
    private double dosage;
    private double ingredientDosage;
    private double ingredientPrice;
    private int addIndex;
    private String matNo;
    private int orgId;
    private String matName;

    public int getProMatId() {
        return proMatId;
    }

    public void setProMatId(int proMatId) {
        this.proMatId = proMatId;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public double getIngredientDosage() {
        return ingredientDosage;
    }

    public void setIngredientDosage(double ingredientDosage) {
        this.ingredientDosage = ingredientDosage;
    }

    public double getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(double ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public int getAddIndex() {
        return addIndex;
    }

    public void setAddIndex(int addIndex) {
        this.addIndex = addIndex;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.proMatId);
        dest.writeDouble(this.dosage);
        dest.writeDouble(this.ingredientDosage);
        dest.writeDouble(this.ingredientPrice);
        dest.writeInt(this.addIndex);
        dest.writeString(this.matNo);
        dest.writeInt(this.orgId);
        dest.writeString(this.matName);
    }

    public MatsBean() {
    }

    protected MatsBean(Parcel in) {
        this.proMatId = in.readInt();
        this.dosage = in.readDouble();
        this.ingredientDosage = in.readDouble();
        this.ingredientPrice = in.readDouble();
        this.addIndex = in.readInt();
        this.matNo = in.readString();
        this.orgId = in.readInt();
        this.matName = in.readString();
    }

    public static final Parcelable.Creator<MatsBean> CREATOR = new Parcelable.Creator<MatsBean>() {
        @Override
        public MatsBean createFromParcel(Parcel source) {
            return new MatsBean(source);
        }

        @Override
        public MatsBean[] newArray(int size) {
            return new MatsBean[size];
        }
    };
}
