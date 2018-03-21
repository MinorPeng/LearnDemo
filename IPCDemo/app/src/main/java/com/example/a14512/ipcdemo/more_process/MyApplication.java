package com.example.a14512.ipcdemo.more_process;

import android.app.Application;
import android.os.Process;
import android.util.Log;

/**
 * @author 14512 on 2017/12/10
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, Process.myPid() + "");
    }
}
