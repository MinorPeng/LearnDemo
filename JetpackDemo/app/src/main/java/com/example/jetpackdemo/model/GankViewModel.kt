package com.example.jetpackdemo.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import com.example.jetpackdemo.bean.DateGank
import com.example.jetpackdemo.bean.GankDataRespository

class GankViewModel(private val dataRepository: GankDataRespository) : ViewModel() {
    fun getGankDatas(): LiveData<List<DateGank.DateGankData>> = dataRepository.getData("2018/08/01")

}
