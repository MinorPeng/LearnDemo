package com.example.jetpackdemo.network

import com.example.jetpackdemo.BASE_URL
import com.example.jetpackdemo.bean.DateGank
import com.example.jetpackdemo.network.retrofit.HttpResponseFun
import com.example.jetpackdemo.network.retrofit.ObservableTransformer
import com.example.jetpackdemo.network.retrofit.RetrofitHelper
import io.reactivex.Observable

/**
 * @author phs on 19-2-6
 */
class NetManager private constructor() {

    private object SingletonHolder {
        val holder = NetManager()
    }

    companion object {
        private val mApiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitHelper.buildRetrofit(BASE_URL).create(ApiService::class.java)
        }

        //Double Check
        val INSTANCE: NetManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NetManager() }

        //静态内部类单例
        fun getInstance(): NetManager {
            return SingletonHolder.holder
        }

        @Synchronized
        fun <T> createApi(url: String, tClass: Class<T>): T {
            return RetrofitHelper.buildRetrofit(url).create(tClass)
        }
    }

    fun getApiService(): ApiService {
        if (mApiService == null) {
            throw NullPointerException("get mApiService must after init")
        }
        return mApiService
    }

    /**
     * 网络请求
     */
    fun getDateGank(date: String): Observable<DateGank> {
        return mApiService.getGankDate(date)
            .compose(ObservableTransformer.transformer())
            .onErrorResumeNext(HttpResponseFun())
    }

}