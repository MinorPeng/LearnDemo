package com.example.a14512.fromsmalltospecialist.fourth.base.e_num;

/**
 * @author 14512 on 2018/4/7
 */

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String mHttpMethod = "";

    HttpMethod(String method) {
        mHttpMethod = method;
    }

    @Override
    public String toString() {
        return mHttpMethod;
    }
}
