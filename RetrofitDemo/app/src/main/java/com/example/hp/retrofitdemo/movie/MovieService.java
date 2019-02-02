package com.example.hp.retrofitdemo.movie;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HP on 2017/4/10.
 */

public interface MovieService {
    //获取豆瓣Top250榜单
    @GET("top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count")int count);
}
