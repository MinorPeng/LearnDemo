package com.example.hp.mvp_rx_retrofit;

import android.app.Application;
import android.content.Context;

import com.example.hp.mvp_rx_retrofit.utils.AppContextUtil;

/**
 * Created by HP on 2017/5/4.
 */

public class MyApplication extends Application {

    public static Context applicationContext;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);
        applicationContext = this;
    }

}
