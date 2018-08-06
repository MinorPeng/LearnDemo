package com.example.a14512.jetpackpagingdemo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.a14512.jetpackpagingdemo.bean.KituItem
import java.util.concurrent.Executor

/**
 * @author 14512 on 2018/7/7
 */
class KituDataSourceFactory(private val retryExecutor: Executor) : DataSource.Factory<String, KituItem>() {
    val sourceLiveData = MutableLiveData<KituDataSource>()

    override fun create(): DataSource<String, KituItem> {
        val source = KituDataSource(retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }

}


