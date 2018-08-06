package com.example.a14512.jetpackpagingdemo

import com.example.a14512.jetpackpagingdemo.bean.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author 14512 on 2018/6/6
 */
object RequestApi {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        api = retrofit.create(Api::class.java)
    }

    fun getData(filter: String, limit: Int, offset: Int): Call<Response> {
        return api.filter(filter, limit, offset)
    }
}

interface Api {
    @GET("anime")
    fun filter(@Query("filter[text]") filter: String,
               @Query("page[limit]") limit: Int,
               @Query("page[offset]") offset: Int): Call<Response>
}