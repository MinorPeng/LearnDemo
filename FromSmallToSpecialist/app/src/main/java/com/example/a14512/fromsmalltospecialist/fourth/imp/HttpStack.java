package com.example.a14512.fromsmalltospecialist.fourth.imp;

import com.example.a14512.fromsmalltospecialist.fourth.base.Request;
import com.example.a14512.fromsmalltospecialist.fourth.base.Response;

/**
 * @author 14512 on 2018/4/7
 */

public interface HttpStack {

    /**
     * 执行HTTP请求
     * @param request
     * @return
     */
    public Response performRequest(Request<?> request);
}
