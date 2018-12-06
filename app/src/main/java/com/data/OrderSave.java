package com.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.huojumu.utils.Constant;

/**
 * @author : Jie
 * Date: 2018/10/22
 * Description:
 */
@Entity(tableName = Constant.ORDER_INFO)
public class OrderSave {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "price")
    private double price;//金额
    @ColumnInfo(name = "time")
    private String time;//时间
    @ColumnInfo(name = "earn1")
    private double earn1;//实收
    @ColumnInfo(name = "earn2")
    private double earn2;//虚收
    @ColumnInfo(name = "orderNo")
    private String orderNo;//订单编号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getEarn1() {
        return earn1;
    }

    public void setEarn1(double earn1) {
        this.earn1 = earn1;
    }

    public double getEarn2() {
        return earn2;
    }

    public void setEarn2(double earn2) {
        this.earn2 = earn2;
    }
}
