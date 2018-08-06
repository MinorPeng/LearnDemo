package com.example.a14512.jetpackpagingdemo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations

/**
 * @author 14512 on 2018/6/6
 */
class KituViewModel: AndroidViewModel {
    private lateinit var mPostRepository: KituRepository
    constructor(application: Application, postRepository: KituRepository):super(application){
        this.mPostRepository = postRepository
    }
    // region 基于Android官方Paging Library的分页加载框架
    private val data = MutableLiveData<String>()
    private val repoResult = Transformations.map(data, {
        mPostRepository.getStudentList(10)
    })
    val posts = Transformations.switchMap(repoResult) { it.pagedList }!!
    val networkState = Transformations.switchMap(repoResult) { it.networkState }!!
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }!!
    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }
    fun showDatas(subreddit: String): Boolean {
        if (data.value == subreddit) {
            return false
        }
        data.value = subreddit
        return true
    }
    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }
    fun currentData(): String? = data.value
    // endregion
//    private var mAllLiveData: LiveData<PagedList<KituItem>> ?= null
//
//    companion object {
//        private const val PAGED_LIST_PAGE_SIZE = 20
//        private const val PAGED_LIST_ENABLE_PLACEHOLDERS = false
//    }
//
//    val all: LiveData<PagedList<KituItem>>
//        get() {
//            if (mAllLiveData == null) {
//                mAllLiveData = LivePagedListBuilder(RequestApi.getData())
//
//            }
//            return mAllLiveData ?: throw AssertionError("check your threads")
//        }
//
//    fun setQuery(query: String) {
//        mAllLiveData = null
//    }
}