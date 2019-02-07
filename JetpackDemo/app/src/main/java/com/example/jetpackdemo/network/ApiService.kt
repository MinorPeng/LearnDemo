package com.example.jetpackdemo.network

import com.example.jetpackdemo.bean.DateGank
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author phs on 19-2-6
 */
interface ApiService {

    @GET("day/{date}")
    fun getGankDate(@Path("date") date: String): Observable<DateGank>

}