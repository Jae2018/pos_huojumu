package com.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.huojumu.utils.Constant;

@Entity(tableName = Constant.ORDER_NO)
public class OrderInfoNo {


    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "proMateID")
    private int proMateID;//商品id
    @ColumnInfo(name = "proMateName")
    private String proMateName;//商品名
    @ColumnInfo(name = "sell")
    private double sell;//单价
    @ColumnInfo(name = "number")
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProMateID() {
        return proMateID;
    }

    public void setProMateID(int proMateID) {
        this.proMateID = proMateID;
    }

    public String getProMateName() {
        return proMateName;
    }

    public void setProMateName(String proMateName) {
        this.proMateName = proMateName;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
