package com.example.a14512.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @author 14512 on 2018/8/6
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
