package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/10/29
 * Description:
 */
public class TaoCanBean {

    private String isChanaged;
    private int proId;
    private int diffPrice;
    private int proCount;
    private int id;
    private int proComboId;
    private int parentId;
    private boolean selected;

    public String getIsChanaged() {
        return isChanaged;
    }

    public void setIsChanaged(String isChanaged) {
        this.isChanaged = isChanaged;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getDiffPrice() {
        return diffPrice;
    }

    public void setDiffPrice(int diffPrice) {
        this.diffPrice = diffPrice;
    }

    public int getProCount() {
        return proCount;
    }

    public void setProCount(int proCount) {
        this.proCount = proCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProComboId() {
        return proComboId;
    }

    public void setProComboId(int proComboId) {
        this.proComboId = proComboId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
