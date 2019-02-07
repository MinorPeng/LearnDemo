package com.example.jetpackdemo.model

import com.example.jetpackdemo.bean.GankDataRespository

/**
 * @author phs on 19-2-7
 */
object InjectorUtil {
    fun provideGankViewModelFactory(): GankViewModelFactory {
        val respository = GankDataRespository.INSTANCE
        return GankViewModelFactory(respository)
    }

}