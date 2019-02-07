package com.example.jetpackdemo.network.retrofit

import com.example.jetpackdemo.network.exception.ExceptionHandle
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author 14512 on 2018/3/17
 */
class HttpResponseFun<T> : Function<Throwable, Observable<T>> {
    override fun apply(t: Throwable): Observable<T> {
        return Observable.error(ExceptionHandle.handleException(t))
    }
}