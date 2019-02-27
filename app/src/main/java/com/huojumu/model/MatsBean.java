package com.huojumu.model;

public class MatsBean {

    /**     加料
     * proMatId : 40
     * dosage : 11
     * ingredientDosage : 5
     * ingredientPrice : 3
     * addIndex : 1
     * matNo : YL-0003
     * orgId : 3
     * matName : 纯牛奶
     */

    private int proMatId;
    private int dosage;
    private int ingredientDosage;
    private int ingredientPrice;
    private int addIndex;
    private String matNo;
    private int orgId;
    private String matName;

    public MatsBean(int proMatId) {
        this.proMatId = proMatId;
    }

//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }

    public int getProMatId() {
        return proMatId;
    }

    public void setProMatId(int proMatId) {
        this.proMatId = proMatId;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getIngredientDosage() {
        return ingredientDosage;
    }

    public void setIngredientDosage(int ingredientDosage) {
        this.ingredientDosage = ingredientDosage;
    }

    public int getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(int ingredientPrice) {
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
