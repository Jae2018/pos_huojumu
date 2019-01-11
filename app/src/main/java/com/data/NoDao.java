package com.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoDao {

    @Query("select * from order_no")
    List<OrderInfoNo> getOrderSaveList();

    @Query("delete from order_no")
    void deleteAll();

    @Query("select * from order_no where proMateID=:proMateID ")
    OrderInfoNo getSingleOrder(int proMateID);

    @Query("update order_no set proMateID=:proMateID where number=:number")
    void updateOrder(int proMateID, int number);

    @Insert
    void save(OrderInfoNo orderDetail);

}
