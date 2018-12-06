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
@Entity(tableName = Constant.ORDER_TEMP)
public class OrderDetail {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "proName")
    private String proName;//商品名
    @ColumnInfo(name = "sell")
    private double sell;//单价


}
