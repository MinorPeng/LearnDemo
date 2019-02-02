package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import java.util.concurrent.Executor

/**
 * @author 14512 on 2018/8/22
 * DataSource负责加载第一页以及后面每一页数据
 */
open class StuDataSource(private val retryExecutor: Executor): ItemKeyedDataSource<String, StuBean>() {
    private val TAG: String = "debug source"
    private var retry:(()->Any)? = null
    private var startPosition: Int = 0

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute { it.invoke() }
        }
    }

    val netWorkState by lazy {
        MutableLiveData<Resource<String>>()
    }

    val initialLoad by lazy {
        MutableLiveData<Resource<String>>()
    }

    /**
     * 接收初始加载的数据，在这里需要将获取到的数据通过LoadInitialCallback的onResult进行回调，
     * 用于出始化PagedList，并对加载的项目进行计数
     */
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<StuBean>) {
        Log.d(TAG, "loadInitial->mSkip:"+startPosition+",count:"+params.requestedLoadSize)
        netWorkState.postValue(Resource.loading(null))
        initialLoad.postValue(Resource.loading(null))

        //耗时
        val list = loadData(startPosition, params.requestedLoadSize)
        retry = null
        netWorkState.postValue(Resource.success(null))
        initialLoad.postValue(Resource.success(null))
        callback.onResult(list)
        startPosition += list.size
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<StuBean>) {
        Log.d(TAG,"loadAfter->mSkip:"+startPosition+",count:"+params.requestedLoadSize)
        netWorkState.postValue(Resource.loading(null))

        //耗时
        val list = loadData(startPosition, params.requestedLoadSize)
        retry = null
        netWorkState.postValue(Resource.success(null))
        callback.onResult(list)
        startPosition += list.size
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<StuBean>) {
        Log.d(TAG,"loadBefore")
    }

    override fun getKey(item: StuBean): String {
        return item.id
    }

    private fun loadData(startPosition: Int, limit: Int): List<StuBean> {
        val list = ArrayList<StuBean>()
        var position: Int
        for (i in 0 until limit) {
            position = startPosition + i
            val data = StuBean(position.toString(), "学生$position")
            list.add(data)
        }
        return list
    }

}