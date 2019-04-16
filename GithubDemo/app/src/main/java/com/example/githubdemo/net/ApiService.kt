package com.example.githubdemo.net

import com.example.githubdemo.moudels.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author 14512 on 2019/4/16
 */
interface ApiService {
    @GET("users/airbnb/repos")
    fun getUsers(@Query("page") page: Int): Observable<ApiResult<List<User>>>
}
