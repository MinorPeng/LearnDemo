package com.example.a14512.weatherkotlin.network

import com.example.a14512.weatherkotlin.BuildConfig
import com.example.a14512.weatherkotlin.WeatherApplication
import com.example.a14512.weatherkotlin.config.BASE_URL
import com.example.a14512.weatherkotlin.network.cookie.PersistenceCookieJar
import com.example.a14512.weatherkotlin.network.cookie.cache.SetCookieCache
import com.example.a14512.weatherkotlin.network.cookie.persistence.SharePrefsCookiePresistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * @author 14512 on 2018/3/17
 */
class RetrofitManager {

    init {
        apiService = build(BASE_URL).create(ApiService::class.java)
    }

    companion object {
        private var apiService: ApiService by Delegates.notNull()

        fun getInstance(): RetrofitManager {
            return RetrofitManager()
        }

        fun getApiService1(): ApiService {
            if (apiService == null) {
                throw NullPointerException("get apiService must be called after init")
            }
            return apiService
        }

        fun <T> createApi(url: String, tClass: Class<T>): T {
            return build(url).create(tClass)
        }

        private fun build(baseUrl: String): Retrofit {
            var retrofit: Retrofit? = null
            synchronized(Retrofit::class.java) {
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        // 使用OkHttp Client
                        .client(buildOkHttpClient())
                        // 集成RxJava处理
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        // 集成Gson转换器
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }

            return retrofit!!
        }

        /**
         * 构建OkHttpClient
         * @return
         */
        private fun buildOkHttpClient(): OkHttpClient {
            //持久化cookie
            val cookieJar = PersistenceCookieJar(SetCookieCache(),
                    SharePrefsCookiePresistor(WeatherApplication.getContext()))
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS
                                        else HttpLoggingInterceptor.Level.NONE
            return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
                    .cookieJar(cookieJar)
                    .retryOnConnectionFailure(true)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build()
        }
    }

    /**
     * 网络请求
     */
    fun getOne() {

    }
}