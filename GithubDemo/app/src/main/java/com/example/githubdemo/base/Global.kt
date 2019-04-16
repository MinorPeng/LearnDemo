package com.example.githubdemo.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment

/**
 * @author 14512 on 2019/4/16
 */
@SuppressLint("StaticFieldLeak")
object Global {
    private var mContext: Context? = null
    private var mApplication: Application? = null
    private var mSDCachePath: String? = null

    fun init(context: Context, application: Application) {
        if (mContext == null || mApplication == null) {
            this.mContext = context
            this.mApplication = application
        }
        mSDCachePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${context.packageName}"
    }

    fun getContext(): Context {
        if (mContext == null) {
            throw RuntimeException("context is null")
        }
        return mContext!!
    }

    fun getApplication(): Application {
        if (mApplication == null) {
            throw RuntimeException("application is null")
        }
        return mApplication!!
    }

}