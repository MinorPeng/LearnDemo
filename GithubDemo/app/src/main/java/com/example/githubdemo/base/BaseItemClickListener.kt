package com.example.githubdemo.base

import android.view.View

/**
 * @author 14512 on 2019/4/17
 */
interface BaseItemClickListener {
    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int)
}