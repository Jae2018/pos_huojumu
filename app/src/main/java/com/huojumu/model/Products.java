package com.huojumu.model;


import java.util.List;

/**
 * @author : Jie
 * Date: 2018/9/29
 * Description: 商品信息
 */
public class Products {

    private String orderId;
    private List<TastesBean> tastes;
    private List<ScaleBean> scales;
    private List<String> makes;
    private List<Production> products;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public List<String> getMakes() {
        return makes;
    }

    public void setMakes(List<String> makes) {
        this.makes = makes;
    }

    public List<Production> getProducts() {
        return products;
    }

    public void setProducts(List<Production> products) {
        this.products = products;
    }

}
