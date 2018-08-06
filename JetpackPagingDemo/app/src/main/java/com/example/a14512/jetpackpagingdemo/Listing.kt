package com.example.a14512.jetpackpagingdemo

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class Listing<T>(val pagedList: LiveData<PagedList<T>>,
                      val networkState: LiveData<Result<String>>,
                      val refreshState: LiveData<Result<String>>,
                      val refresh: () -> Unit,
                      val retry: () -> Unit)
