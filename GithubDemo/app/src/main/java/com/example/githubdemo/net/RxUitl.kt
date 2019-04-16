package com.example.githubdemo.net

import com.example.githubdemo.common.utils.LogUtil
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * @author 14512 on 2019/2/17
 */

/**
 * 在UI线程和IO线程中切换
 */
object SchedulersTransform {

    fun<T> transformMain(): ObservableTransformer<T, T> {
        return ObservableTransformer {
                upstream -> upstream.subscribeOn(Schedulers.io())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
        }
    }
}

/**
 * 通过map转换
 */
class ResultFunc<T> : Function<ApiResult<T>, T> {
    override fun apply(@NonNull t: ApiResult<T>): T {
        LogUtil.e(javaClass.name, "${t.message} ${t.code} ${t.success}")
        return t.data
    }
}
