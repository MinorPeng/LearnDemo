package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.util.Log

/**
 * @author 14512 on 2018/8/23
 */
class StuViewModel(application: Application, postRepository: IStuRepository) : AndroidViewModel(application) {

    private var mPostRepository: IStuRepository = postRepository
    private val data = MutableLiveData<String>()
    private val repoResult = Transformations.map(data) {
        mPostRepository.getStudentList(10)
    }

    val posts = Transformations.switchMap(repoResult) { it.pagedList }!!
    val netWorkState = Transformations.switchMap(repoResult) { it.newWorkStat }!!
    val refreState = Transformations.switchMap(repoResult) { it.refreshState }!!

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
        val list = repoResult.value
        list?.retry?.invoke()
    }

    fun currentData(): String? = data.value

}