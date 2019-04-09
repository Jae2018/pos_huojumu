package com.huojumu.model;

public class MatsBean {

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
}
