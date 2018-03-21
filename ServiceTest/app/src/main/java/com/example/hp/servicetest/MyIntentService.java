package com.example.hp.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by HP on 2017/2/9.
 */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");  //调用父类的有参构造函数
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //打印当前线程的id
        Log.d("MyIntentService", "Thread id is " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy");
    }
}
