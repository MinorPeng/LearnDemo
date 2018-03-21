package com.example.hp.mvp_rx_retrofit.model;

import com.example.hp.mvp_rx_retrofit.base.IBaseRequestCallBack;

/**
 * 描述：MVP中的M；处理获取网络天气数据
 * Created by HP on 2017/5/4.
 */

public interface WeatherModel<T> {

    /**
     * @descriptoin	获取网络数据
     * @param city 城市
     *             @param key key
     *                        @param iBaseRequestCallBack 数据的回调接口
     */
    void loadWeather(String city,String key, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @param
     */
    void onUnsubscribe();
}
