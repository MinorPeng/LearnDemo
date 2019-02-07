package com.example.jetpackdemo.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.jetpackdemo.bean.GankDataRespository

/**
 * @author phs on 19-2-7
 */
@Suppress("UNCHECKED_CAST")
class GankViewModelFactory(private val respository: GankDataRespository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GankViewModel(respository) as T
    }
}