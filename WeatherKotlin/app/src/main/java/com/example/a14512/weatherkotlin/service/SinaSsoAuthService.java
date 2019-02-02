package com.example.a14512.weatherkotlin.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.a14512.fromsmalltospecialist.SsoAuth;
import com.example.a14512.weatherkotlin.utils.PLog;

/**
 * @author 14512 on 2018/3/24
 */

public class SinaSsoAuthService extends Service {

    SinaSsoImpl mBinder = new SinaSsoImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        PLog.INSTANCE.e("create");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class SinaSsoImpl extends SsoAuth.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void ssoAuth(String userName, String pwd) throws RemoteException {
            PLog.INSTANCE.e("执行SSO"+userName +"pwd"+ pwd);
        }
    }
}
