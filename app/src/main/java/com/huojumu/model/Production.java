package com.huojumu.model;

public class Production {

    private int id;
    private String name;
    private String addOn;
    private int number;
    //价格
    private int price;
    //材料
    private String parts;
    //图片
    private String url;
    //优惠活动
    private int cut;
    private String cutStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddOn() {
        return addOn;
    }

    public void setAddOn(String addOn) {
        this.addOn = addOn;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCut() {
        return cut;
    }

    public void setCut(int cut) {
        this.cut = cut;
    }

    public String getCutStr() {
        return cutStr;
    }

    public void setCutStr(String cutStr) {
        this.cutStr = cutStr;
    }
}
