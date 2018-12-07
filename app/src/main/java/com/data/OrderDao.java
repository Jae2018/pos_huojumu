package com.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/22
 * Description:
 */
@Dao
public interface OrderDao {

    @Query("select * from order_table")
    List<OrderSave> getOrderSaveList();

    @Query("delete from order_table")
    void deleteAll();

    @Query("delete from order_table where orderNo=:orderNo")
    void deleteSingle(String orderNo);

    @Query("select * from order_table where orderNo=:orderNo ")
    OrderSave getSingleOrder(String orderNo);

    @Update
    void updateOrder(OrderSave orderSave);

    @Insert
    void save(OrderSave orderSave);

}
