package com.data;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/12/6
 * Description: qq：494669467，wx：s494669467
 */
public interface DetailDao {

    @Query("select * from order_temp")
    List<OrderDetail> getOrderSaveList();

    @Query("delete from order_temp")
    void deleteAll();

    @Query("select * from order_temp where :proName ")
    OrderDetail getSingleOrder(String proName);

    @Update
    void updateOrder(OrderDetail orderDetail);

    @Insert
    void save(OrderDetail orderDetail);


}
