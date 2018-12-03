package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/10/19
 * Description:
 */
public class InventoryDetail {
    /**
     * cehckId : 9
     * unitName : 个
     * checkDetailId : 73
     * matId : 1
     * unitId : 22
     * diffStocks : 60
     * correctedStocks : 366
     * stocks : 306
     */

    private int cehckId;
    private String unitName;
    private int checkDetailId;
    private int matId;
    private int unitId;
    private int diffStocks;
    private int correctedStocks;
    private int stocks;

    public int getCehckId() {
        return cehckId;
    }

    public void setCehckId(int cehckId) {
        this.cehckId = cehckId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getCheckDetailId() {
        return checkDetailId;
    }

    public void setCheckDetailId(int checkDetailId) {
        this.checkDetailId = checkDetailId;
    }

    public int getMatId() {
        return matId;
    }

    public void setMatId(int matId) {
        this.matId = matId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getDiffStocks() {
        return diffStocks;
    }

    public void setDiffStocks(int diffStocks) {
        this.diffStocks = diffStocks;
    }

    public int getCorrectedStocks() {
        return correctedStocks;
    }

    public void setCorrectedStocks(int correctedStocks) {
        this.correctedStocks = correctedStocks;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }
}
