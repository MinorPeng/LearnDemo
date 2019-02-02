package com.example.hp.mvp_rx_retrofit.base;

import com.example.hp.mvp_rx_retrofit.helper.RetrofitManager;

/**
 * 描述：业务对象的基类
 * Created by HP on 2017/5/4.
 */

public class BaseModel {

    //retrofit请求数据的管理类
    public RetrofitManager retrofitManager;

    public BaseModel() {
        retrofitManager = RetrofitManager.builder();
    }
}
