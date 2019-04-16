package com.example.githubdemo.net

import com.example.githubdemo.BuildConfig
import com.example.githubdemo.base.Global
import com.example.githubdemo.common.BASE_URL
import com.example.githubdemo.common.utils.NetworkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitManager{
    val netWorkCache = Global.getContext().cacheDir.absolutePath +  File.separator + "NetCache"
    lateinit var sOkHttpClient: OkHttpClient
    lateinit var sRetrofit: Retrofit
    lateinit var mNetService: NetService

    init {
        initOKHttp()
        initRetrofit()
        initNetService()
    }

    @Synchronized
    private fun initNetService() {
        mNetService = NetService(sRetrofit.create(ApiService::class.java))
    }

    private fun initOKHttp(){
        val builder = OkHttpClient.Builder()
        val cacheFile = File(netWorkCache)
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        val cacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtils.isNetworkConnected(Global.getContext())) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            val newBuilder = response.newBuilder()
            if (NetworkUtils.isNetworkConnected(Global.getContext())) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时
                newBuilder.header("Cache-Control", "public, max-age=$maxAge")
            } else {
                // 无网络时，设置超时为4周
                val maxStale = 60 * 60 * 24 * 28
                newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
            }
            newBuilder.build()
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.HEADERS
            HttpLoggingInterceptor.Level.BODY
//            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE
//            HttpLoggingInterceptor.Level.HEADERS
//            HttpLoggingInterceptor.Level.BODY
        }

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        sOkHttpClient = builder.build()
    }

    private fun initRetrofit() {
        sRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(sOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
