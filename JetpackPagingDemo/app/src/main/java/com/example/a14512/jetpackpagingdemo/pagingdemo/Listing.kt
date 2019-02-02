package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

/**
 * @author 14512 on 2018/8/22
 * UI显示列表和系统其余部分进行交互所必需的数据类
 */
data class Listing<T>(val pagedList: LiveData<PagedList<T>>,
                      val newWorkStat: LiveData<Resource<String>>,
                      val refreshState: LiveData<Resource<String>>,
                      val refresh: () -> Unit,
                      val retry: () -> Unit)