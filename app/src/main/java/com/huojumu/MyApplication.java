package com.huojumu;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.data.DataBase;
import com.huojumu.utils.Constant;
import com.huojumu.utils.PrinterUtil;
import com.huojumu.utils.SpUtil;

import java.util.UUID;


/**
 * @author : Jie
 * Date: 2018/5/25
 * <p>
 * Description:
 */
public class MyApplication extends Application {

    static DataBase db;
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();

//        initLeakCanary();
        initDataBase();
        SpUtil.Instance(this);
        SpUtil.save(Constant.UUID, UUID.randomUUID().toString());
        PrinterUtil.connectPrinter(getApplicationContext());
        //, String.format(Constant.BAND, uuid)
    }

    private void initDataBase() {
        db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "pos_db")
//                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    public static DataBase getDb() {
        return db;
    }

    private void initLeakCanary() {
        // 内存泄露检查工具
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }


}
