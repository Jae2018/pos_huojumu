package com.huojumu.model;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/11/6
 * Description:
 */
public class OrdersList {
    /**
     * mats : []
     * orderNo : DDGH-CN12-0002-0001-0001201805090003
     * tastes : []
     * makes : []
     * id : 309
     * proName : 焦糖面包
     * status : 2
     */

    private String orderNo;
    private int id;
    private String proName;
    private String status;
    private List<?> mats;
    private List<?> tastes;
    private List<?> makes;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<?> getMats() {
        return mats;
    }

    public void setMats(List<?> mats) {
        this.mats = mats;
    }

    public List<?> getTastes() {
        return tastes;
    }

    public void setTastes(List<?> tastes) {
        this.tastes = tastes;
    }

    public List<?> getMakes() {
        return makes;
    }

    public void setMakes(List<?> makes) {
        this.makes = makes;
    }
}
