package com.example.a14512.mvvmdemo.net;

import com.example.a14512.mvvmdemo.mode.News;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 14512 on 2018/8/20
 */
public interface ApiService {
    String BASE_URL = "http://api.tianapi.com/";

    /**
     * get news
     * @param key
     * @param num
     * @return
     */
    @POST("it")
    Observable<News> getNews(@Query("key")String key, @Query("num")String num);
}
