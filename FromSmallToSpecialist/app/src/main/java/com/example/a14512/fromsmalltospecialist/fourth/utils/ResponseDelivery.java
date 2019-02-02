package com.example.a14512.fromsmalltospecialist.fourth.utils;

import android.support.annotation.NonNull;

import com.example.a14512.fromsmalltospecialist.fourth.base.Request;
import com.example.a14512.fromsmalltospecialist.fourth.base.Response;

import java.util.concurrent.Executor;

/**
 * @author 14512 on 2018/4/7
 */

public class ResponseDelivery implements Executor {
//    Handler mResponseHandler = new Handler(Looper.getMainLooper());

    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };
        execute(runnable);
    }

    @Override
    public void execute(@NonNull Runnable command) {

    }
}
