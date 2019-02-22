package com.huojumu.model;

public class TastesBean {
    /**     口味
     * tasteId : 19
     * tasteName : 微辣
     * groupId : 1
     * multiple : 0.1
     */

    private int tasteId;
    private String tasteName;
    private int groupId;
    private double multiple;
//    private boolean isSelected;

    public TastesBean(int tasteId) {
        this.tasteId = tasteId;
    }
//
//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }
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
}
