package com.huojumu.model;

public class TastesBean {
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
}
