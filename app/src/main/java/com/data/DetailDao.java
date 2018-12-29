package com.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/12/6
 * Description: qq：494669467，wx：s494669467
 */
@Dao
public interface DetailDao {

    @Query("select * from order_temp")
    List<OrderDetail> getOrderSaveList();

    @Query("delete from order_temp")
    void deleteAll();

    @Query("select * from order_temp where proName=:proName ")
    OrderDetail getSingleOrder(String proName);

    @Query("update order_temp set proName=:proName where number=:number")
    void updateOrder(String proName,int number);

    @Insert
    void save(OrderDetail orderDetail);


}