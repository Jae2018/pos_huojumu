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
    private float price;//金额
    @ColumnInfo(name = "time")
    private String time;//时间
    @ColumnInfo(name = "earn1")
    private float earn1;//实收
    @ColumnInfo(name = "earn2")
    private float earn2;//虚收
    @ColumnInfo(name = "orderNo")
    private String orderNo;//订单编号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getEarn1() {
        return earn1;
    }

    public void setEarn1(float earn1) {
        this.earn1 = earn1;
    }

    public float getEarn2() {
        return earn2;
    }

    public void setEarn2(float earn2) {
        this.earn2 = earn2;
    }
}
