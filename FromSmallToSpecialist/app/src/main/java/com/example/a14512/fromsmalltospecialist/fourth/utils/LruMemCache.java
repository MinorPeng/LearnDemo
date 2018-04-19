package com.example.a14512.fromsmalltospecialist.fourth.utils;


import android.util.LruCache;

import com.example.a14512.fromsmalltospecialist.fourth.base.Response;
import com.example.a14512.fromsmalltospecialist.fourth.imp.Cache;

/**
 * @author 14512 on 2018/4/7
 */

public class LruMemCache implements Cache<String,Response> {

    private LruCache<String, Response> mResponseLruCache;

    public LruMemCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //取1/8作为缓存
        final int cacheSize = maxMemory / 8;
        mResponseLruCache = new LruCache<String, Response>(cacheSize) {
            @Override
            protected int sizeOf(String key, Response value) {
                return value.rawData.length / 1024;
            }
        };
    }

    @Override
    public Response get(String key) {
        return mResponseLruCache.get(key);
    }

    @Override
    public void put(String key, Response value) {
        mResponseLruCache.put(key, value);
    }

    @Override
    public void remove(String key) {
        mResponseLruCache.remove(key);
    }
}
