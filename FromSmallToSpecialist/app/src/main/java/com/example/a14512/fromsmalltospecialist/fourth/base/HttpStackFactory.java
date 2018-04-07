package com.example.a14512.fromsmalltospecialist.fourth.base;

import android.os.Build;

import com.example.a14512.fromsmalltospecialist.fourth.HttpStack;

/**
 * @author 14512 on 2018/4/7
 */

public final class HttpStackFactory {

    /**
     * API 9
     */
    private static final int GINGEBRRED_SDK_NUM = 9;

    /**
     * @return api>9使用HttpURLConnection，api<9使用HttpClientConnection
     */
    public static HttpStack creatrHttpStack() {
        int runtimeSDKApi = Build.VERSION.SDK_INT;
        if (runtimeSDKApi >= GINGEBRRED_SDK_NUM) {
            return new HttpUrlConnectionStack();
        } else {
            return new HttpClientConnectionStack();
        }
    }

}
