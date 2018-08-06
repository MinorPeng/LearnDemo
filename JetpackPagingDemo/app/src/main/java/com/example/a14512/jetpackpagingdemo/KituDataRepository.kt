package com.example.a14512.jetpackpagingdemo

import android.arch.lifecycle.Transformations.switchMap
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.a14512.jetpackpagingdemo.bean.KituItem
import java.util.concurrent.Executor

/**
 * @author 14512 on 2018/7/7
 */
class KituDataRepository(private val executor: Executor) : KituRepository {
    override fun getStudentList(pageSize: Int): Listing<KituItem?> {
        val sourceFactory = KituDataSourceFactory(executor)
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize*2)//定义第一页加载项目的数量
                .setPageSize(pageSize)//定义从DataSource中每一次加载的项目数量
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .setFetchExecutor(executor)//设置Executor执行器用于从用于从DataSources中获取PagedLists数据，如果未设置，则默认为Arch组件I/O线程。
                .build()
        val refreshState = switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing<KituItem?>(
                pagedList =pagedList,
                networkState = switchMap(sourceFactory.sourceLiveData, {
                    it.networkState
                }),
                retry = {
                    sourceFactory.sourceLiveData.value?.retryFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState)
    }
}