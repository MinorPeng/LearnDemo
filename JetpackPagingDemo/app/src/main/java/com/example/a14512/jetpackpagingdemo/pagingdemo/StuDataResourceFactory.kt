package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import java.util.concurrent.Executor

/**
 * @author 14512 on 2018/8/22
 * 一个简单的数据源工厂，它提供了一种观察上次创建的数据源的方式，这使得我们能够将其网络请求状态等返回到UI界面
 */
class StuDataResourceFactory(private val retryExecutor: Executor):
        DataSource.Factory<String, StuBean>() {
    private val sourceLiveData = MutableLiveData<StuDataSource>()

    override fun create(): DataSource<String, StuBean> {
        val source = StuDataSource(retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }

    fun getSourceLiveData(): MutableLiveData<StuDataSource> {
        return sourceLiveData
    }
}