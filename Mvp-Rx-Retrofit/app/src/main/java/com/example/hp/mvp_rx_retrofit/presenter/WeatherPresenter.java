package com.example.hp.mvp_rx_retrofit.presenter;

/**
 * 描述：MVP中的P接口定义
 * Created by HP on 2017/5/4.
 */

public interface WeatherPresenter {

    /**
     * @descriptoin	请求天气数据
     * @param key key
     *            @param city 城市
     * @return
     */
    void loadWeather(String key, String city);

    /**
     * @descriptoin	注销subscribe
     */
    void unSubscribe();
}
