package com.minorpeng.litepaldemo;

import android.app.Application;

import org.litepal.LitePal;

/**
 * @author hesheng1024
 * @date 2020/5/1 10:55
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
