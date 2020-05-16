package com.hesheng1024.jetpackdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * @author hesheng1024
 * @date 2020/5/16 10:50
 */
class MainViewModelFactory(private val count: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(count) as T
    }
}