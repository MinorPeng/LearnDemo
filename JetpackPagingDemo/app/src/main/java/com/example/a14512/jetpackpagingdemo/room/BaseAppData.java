package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * @author 14512 on 2018/9/2
 */
@Database(entities = {User.class}, version = 1)
@TypeConverters(DateConverters.class)
public abstract class BaseAppData extends RoomDatabase {
    public abstract UserDao userDao();

    //创建实例
//    BaseAppData db = Room
//            .databaseBuilder(ApplicationContext, BaseAppData.class, "database-name")
//            .build()
}
