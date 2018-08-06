package com.example.a14512.jetpackpagingdemo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.example.a14512.jetpackpagingdemo.bean.KituItem
import java.util.concurrent.Executor

class KituDataSource(private val retryExecutor: Executor) : ItemKeyedDataSource<String, KituItem>() {
    private val TAG: String = "paging"
    private var retry: (()->Any)? = null
    private var startPosition: Int = 0

    fun retryFailed() {
        val prevRetry=retry
        retry=null
        prevRetry?.let {
            retryExecutor.execute { it.invoke() }
        }
    }

    val networkState by lazy{
        MutableLiveData<Result<String>>()
    }
    val initialLoad by lazy{
        MutableLiveData<Result<String>>()
    }

    /**
     * 接收初始加载的数据，在这里需要将获取到的数据通过LoadInitialCallback的onResult进行回调，用于出始化PagedList，并对加载的项目进行计数
     */
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<KituItem>) {
        Log.d(TAG,"loadInitial->mSkip:"+startPosition+",count:"+params.requestedLoadSize)
        networkState.postValue(Result.loading(null))
        initialLoad.postValue(Result.loading(null))
        //模拟耗时操作
        val list = loadData(startPosition, params.requestedLoadSize)
        retry=null
        networkState.postValue(Result.success(null))
        initialLoad.postValue(Result.success(null))
        callback.onResult(list)
        startPosition += list.size
    }

    /**
     * 接收加载的数据
     */
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<KituItem>) {
        Log.d(TAG,"loadAfter->mSkip:"+startPosition+",count:"+params.requestedLoadSize)
        networkState.postValue(Result.loading(null))
        //模拟耗时操作
        val list=loadData(startPosition, params.requestedLoadSize)
        retry=null
        networkState.postValue(Result.success(null))
        callback.onResult(list)
        startPosition+=list.size
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<KituItem>) {
        Log.d(TAG,"loadBefore")
    }

    override fun getKey(item: KituItem): String = item.id.toString()

    /**
     * 模拟耗时操作，假设这里需要做一些后台线程的数据加载任务。
     */
    private fun loadData(startPosition: Int, limit: Int): List<KituItem> {
        val list = ArrayList<KituItem>()
        for (i in 0 until limit) {
            var position = startPosition + i
            val data = KituItem(position, "学生@$position", null)
            list.add(data)
        }
        return list
    }

}
