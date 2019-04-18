package com.example.githubdemo.base

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.githubdemo.common.utils.LogUtil
import com.example.githubdemo.moudels.User
import com.example.githubdemo.moudels.business.UserDao

/**
 * @author 14512 on 2019/4/18
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private val TAG = "AppDatabase"
        private val DATABASE_NAME = "github-demo-db"
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                //TODO 数据库更改升级
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        LogUtil.i(TAG, "database onCreate")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        LogUtil.i(TAG, "database onOpen")
                    }
                }).build()
        }
    }

    abstract fun userDao(): UserDao
}