package com.huojumu.model;


import java.util.List;

public class Production {


    private List<MatsBean> mats;
    private List<ImgsBean> imgs;
    private List<TastesBean> tastes;
    private List<MakesBean> makes;
    private List<TaoCanBean> taocan;
    private String proType;
    private int typeId;
    private int number = 1;
    private String addon;
    private String tasteStr;
    private String scaleStr;
    private String proAlsname;
    private String matStr;
    private double mateP;//加料价格
    private double price;//单品价格

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getProAlsname() {
        return proAlsname;
    }

    public void setProAlsname(String proAlsname) {
        this.proAlsname = proAlsname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * proNo : CP-CN32-0001-0004
     * imgs : []
     * proId : 4
     * minPrice : 0.01
     * proName : 什么都有奶绿
     */

    private String proNo;
    private int proId;
    private double minPrice;
    private String proName;

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public double getMateP() {
        return mateP;
    }

    public void setMateP(double mateP) {
        this.mateP = mateP;
    }

    public String getMatStr() {
        return matStr;
    }

    public void setMatStr(String matStr) {
        this.matStr = matStr;
    }



    public List<MatsBean> getMats() {
        return mats;
    }

    public void setMats(List<MatsBean> mats) {
        this.mats = mats;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    public List<TastesBean> getTastes() {
        return tastes;
    }

    public void setTastes(List<TastesBean> tastes) {
        this.tastes = tastes;
    }

    public List<MakesBean> getMakes() {
        return makes;
    }

    public void setMakes(List<MakesBean> makes) {
        this.makes = makes;
    }

    public List<TaoCanBean> getTaocan() {
        return taocan;
    }

    public void setTaocan(List<TaoCanBean> taocan) {
        this.taocan = taocan;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public String getTasteStr() {
        return tasteStr;
    }

    public void setTasteStr(String tasteStr) {
        this.tasteStr = tasteStr;
    }

    public String getScaleStr() {
        return scaleStr;
    }

    public void setScaleStr(String scaleStr) {
        this.scaleStr = scaleStr;
    }


    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

}
