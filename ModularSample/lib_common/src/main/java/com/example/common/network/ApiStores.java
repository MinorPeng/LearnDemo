package com.example.common.network;

import com.example.common.model.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author 14512 on 2018/4/19
 */
public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";


    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<WeatherModel> loadWeatherData(@Path("cityId") String cityId);
}
