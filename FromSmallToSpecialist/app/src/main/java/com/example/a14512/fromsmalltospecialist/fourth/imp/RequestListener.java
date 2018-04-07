package com.example.a14512.fromsmalltospecialist.fourth.imp;

/**
 * @author 14512 on 2018/4/7
 */

public interface RequestListener<T> {

    /**
     * 回调
     * @param stCode
     * @param result
     * @param msg
     */
    public void onComplete(int stCode, T result, String msg);
}
