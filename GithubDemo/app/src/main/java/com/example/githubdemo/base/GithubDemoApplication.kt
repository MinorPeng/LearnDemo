package com.example.githubdemo.base

import android.app.Application
import android.content.Context

/**
 * @author 14512 on 2019/4/16
 */
class GithubDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Global.init(base!!, this)
    }
}