package com.example.a14512.weatherkotlin

import android.app.Application
import android.content.Context
import android.util.Log
import kotlin.properties.Delegates

/**
 * @author 14512 on 2018/3/17
 */
class WeatherApplication : Application() {

//    单例模式
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        private var context: Application ?= null
//        fun mContext() = context!!
//    }

    //Kotlin自带单例模式，委托
    companion object {
        private var mContext : WeatherApplication by Delegates.notNull()
        var mCacheDir : String by Delegates.notNull()

        fun getContext(): Context = mContext

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val startTime = System.currentTimeMillis()
        Log.e("startTime", startTime.toString() + "")
    }


    override fun onCreate() {
        super.onCreate()
        mContext = this
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        mCacheDir = if (applicationContext.externalCacheDir != null) {
            applicationContext.externalCacheDir.toString()
        } else {
            applicationContext.cacheDir.toString()
        }
    }

}