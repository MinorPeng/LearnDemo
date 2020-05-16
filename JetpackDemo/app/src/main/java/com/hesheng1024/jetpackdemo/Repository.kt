package com.hesheng1024.jetpackdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 *
 * @author hesheng1024
 * @date 2020/5/16 11:19
 */
object Repository {

    fun getStr(count: Int): LiveData<String> {
        val liveData = MutableLiveData<String>()
        liveData.value = "set count:$count"
        return liveData
    }
}