package com.example.hp.rxjava_retrofit.base;

import android.app.Application;

/**
 * Created by HP on 2017/5/4.
 */

public class BaseApplication extends Application {

    private static BaseApplication myApplication = null;

    public static BaseApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }
}
