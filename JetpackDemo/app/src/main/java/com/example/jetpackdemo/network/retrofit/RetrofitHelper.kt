package com.example.jetpackdemo.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author phs on 19-2-6
 * 获取Retrofit的工具类
 * 防止多个url的情况获取Retrofit
 */
class RetrofitHelper {

    companion object {
        fun buildRetrofit(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        /**
         * 构建OkHttpClient
         * @return
         */
        private fun buildOkHttpClient(): OkHttpClient {
            //持久化cookie
            return OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
        }
    }
}