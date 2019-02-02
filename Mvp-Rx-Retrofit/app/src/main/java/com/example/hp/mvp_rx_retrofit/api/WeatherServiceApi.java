package com.example.hp.mvp_rx_retrofit.api;

import com.example.hp.mvp_rx_retrofit.bean.WeatherInfoBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：retrofit的接口service定义
 * Created by HP on 2017/5/4.
 */

public interface WeatherServiceApi {

    //请求的接口地址：http://api.avatardata.cn/Weather/Query?key=75bfe88f27a34311a41591291b7191ce&cityname=%E9%95%BF%E6%B2%99
    @GET("Weather/Query?")
    Observable<WeatherInfoBean> loadWeatherInfo(@Query("key") String key, @Query("cityname") String cityname);

}
