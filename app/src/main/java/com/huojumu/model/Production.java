package com.huojumu.model;


import java.util.ArrayList;
import java.util.List;

public class Production {


    /**
     * mats : [{"proMatId":4181,"dosage":1,"ingredientDosage":1,"ingredientPrice":1,"addIndex":1,"matNo":"YL-0106","orgId":106,"matName":"椰果"},{"proMatId":4182,"dosage":1,"ingredientDosage":1,"ingredientPrice":1,"addIndex":2,"matNo":"YL-0105","orgId":105,"matName":"珍珠"}]
     * imgs : [{"path":"https://www.goodb2b.cn/pos_file/default.png","sourceType":"1"}]
     * proType : 1
     * proAlsname : GN
     * tastes : [{"tasteId":2,"tasteName":"默认口味","groupId":1,"multiple":1},{"tasteId":10,"tasteName":"无糖","groupId":1,"multiple":0},{"tasteId":9,"tasteName":"少糖","groupId":1,"multiple":0.2}]
     * proSpellname : gangnai
     * isDiscount : 0
     * proNo : CP-CN51-0001-0168
     * isMoneyOff : 0
     * proId : 325
     * minPrice : 10
     * scales : [{"scaleId":5,"scaName":"纸杯（热）","unitName":"ml","price":10,"proId":325,"unitId":20,"type":"2","capacity":500},{"scaleId":6,"scaName":"瓶装（冻）","unitName":"ml","price":10,"proId":326,"unitId":20,"type":"1","capacity":500}]
     * isBargain : 1
     * isPresented : 0
     * typeId : 59
     * proName : 港奶
     */

    private String proType;
    private String proAlsname;
    private String proSpellname;
    private String isDiscount;//是否打折:null/0-否;1-是
    private String proNo;
    private String isMoneyOff;//是否满减:null/0-否;1-是
    private int proId;
    private double minPrice;
    private String isBargain;//是否特价:null/0-否;1-是
    private String isPresented;//是否满赠:null/0-否;1-是
    private int typeId;//小类id
    private String proName;
    private List<MatsBean> mats;
    private List<ImgsBean> imgs;
    private List<TastesBean> tastes;
    private List<ScaleBean> scales;
    private String planFlag;
    //额外增加
    private int number = 1;//默认数量
    private String addon = "";//备注
    private String tasteStr = "默认口味";//口味
    private String scaleStr = "";//规格
    private String matStr = "不加料";//加料字符串
    private double mateP;//加料价格
    private double scalePrice;//现价
    private double origionPrice;//原价
    private String activeStr = "";//活动内容说明
    private List<ActivesBean> activesBeanList = new ArrayList<>();//商品参与的活动信息集合

    public String getActiveStr() {
        return activeStr;
    }

    public void setActiveStr(String activeStr) {
        this.activeStr = activeStr;
    }

    public List<ActivesBean> getActivesBeanList() {
        return activesBeanList;
    }

    public void setActivesBeanList(List<ActivesBean> activesBeanList) {
        this.activesBeanList = activesBeanList;
    }

    public String getPlanFlag() {
        return planFlag;
    }

    public void setPlanFlag(String planFlag) {
        this.planFlag = planFlag;
    }

    public double getScalePrice() {
        return scalePrice;
    }

    public void setScalePrice(double scalePrice) {
        this.scalePrice = scalePrice;
    }

    public double getOrigionPrice() {
        return origionPrice;
    }

    public void setOrigionPrice(double origionPrice) {
        this.origionPrice = origionPrice;
    }

    public String getMatStr() {
        return matStr;
    }

    public void setMatStr(String matStr) {
        this.matStr = matStr;
    }

    public double getMateP() {
        return mateP;
    }

    public void setMateP(double mateP) {
        this.mateP = mateP;
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

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getProAlsname() {
        return proAlsname;
    }

    public void setProAlsname(String proAlsname) {
        this.proAlsname = proAlsname;
    }

    public String getProSpellname() {
        return proSpellname;
    }

    public void setProSpellname(String proSpellname) {
        this.proSpellname = proSpellname;
    }

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public String getIsMoneyOff() {
        return isMoneyOff;
    }

    public void setIsMoneyOff(String isMoneyOff) {
        this.isMoneyOff = isMoneyOff;
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

    public String getIsBargain() {
        return isBargain;
    }

    public void setIsBargain(String isBargain) {
        this.isBargain = isBargain;
    }

    public String getIsPresented() {
        return isPresented;
    }

    public void setIsPresented(String isPresented) {
        this.isPresented = isPresented;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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

    public List<ScaleBean> getScales() {
        return scales;
    }

    public void setScales(List<ScaleBean> scales) {
        this.scales = scales;
    }

}
