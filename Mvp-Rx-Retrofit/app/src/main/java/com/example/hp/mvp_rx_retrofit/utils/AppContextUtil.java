package com.example.hp.mvp_rx_retrofit.utils;

import android.content.Context;

/**
 * 获取Context
 * Created by HP on 2017/5/4.
 */

public class AppContextUtil {

    private static Context sContext;

    private AppContextUtil() {

    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        if (sContext == null) {
            throw new NullPointerException("the context is null," +
                    "please init AppContextUtil in Application first.");
        }
        return sContext;
    }
}
