package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.arch.lifecycle.Transformations.switchMap
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import java.util.concurrent.Executor

/**
 * @author 14512 on 2018/8/22
 * Repository实现返回一个直接从网络加载数据的Listing，并使用该名称作为加载上一页/下一页数据的关键
 */
class StuDataRepository(private val executor: Executor): IStuRepository {

    override fun getStudentList(pageSize: Int): Listing<StuBean?> {
        val sourceFactory = StuDataResourceFactory(executor)
        val pagingListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)  //定义第一页加载项目的数量
                .setPageSize(pageSize)  //定义从DataSource中每一次加载的项目数量
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, pagingListConfig)
                //设置Executor执行器用于从用于从DataSources中获取PagedLists数据，如果未设置，则默认为Arch组件I/O线程
                .setFetchExecutor(executor)
                .build()
        val refreshState = switchMap(sourceFactory.getSourceLiveData()) {
            it.initialLoad
        }

        return Listing<StuBean?>(pagedList = pagedList,
                newWorkStat = switchMap(sourceFactory.getSourceLiveData()) {
                    it.netWorkState },
                retry = {
                    sourceFactory.getSourceLiveData().value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.getSourceLiveData().value?.invalidate()
                },
                refreshState = refreshState)
    }

}