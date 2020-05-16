package com.hesheng1024.jetpackdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 *
 * @author hesheng1024
 * @date 2020/5/16 10:23
 */

class MainViewModel(count: Int) : ViewModel() {

    // var counter = count

    val counter = MutableLiveData<Int>()

    // 进行数据转换
    val str: LiveData<String> = Transformations.map(counter) {
        "count:$it"
    }

    // 使用SwitchMap，获取另外方法返回的LiveData转换为一个固定的LiveData，使Activity observe
    val smapStr: LiveData<String> = Transformations.switchMap(counter) {
        Repository.getStr(it)
    }

    init {
        counter.value = count
    }

    fun plusOne() {
        val count = counter.value ?: 0
        counter.value = count + 1
    }

    fun clear() {
        counter.value = 0
    }
}