package com.huojumu.model;

public class ScaleBean {

    /**
     * scaleId : 6
     * scaName : 常温大杯
     * unitName : ml
     * unitId : 20
     * type : 3
     * capacity : 750.0
     */

    private int scaleId;
    private String scaName;
    private String unitName;
    private int unitId;
    private String type;
    private double capacity;

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
    public String toString() {
        return "ScaleBean{" +
                "scaleId=" + scaleId +
                ", scaName='" + scaName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", unitId=" + unitId +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
