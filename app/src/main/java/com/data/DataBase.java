package com.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @author : Jie
 * Date: 2018/10/22
 * Description:
 */
@Database(entities = {OrderSave.class, OrderDetail.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public abstract OrderDao getOrderDao();

//    public abstract TempDao getTempDao();

}
