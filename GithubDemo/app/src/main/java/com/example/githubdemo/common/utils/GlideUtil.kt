package com.example.githubdemo.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubdemo.R


/**
 * @author 14512 on 2019/3/4
 */
object GlideUtil {

    fun portrait(context: Context, url: String?, iv: ImageView) {
        LogUtil.e(javaClass.name, "portrait $url")
        val options = RequestOptions().circleCrop()
        Glide.with(context).load(url).apply(options).into(iv)
    }

    fun circle(context: Context, url: String?, iv: ImageView) {
        val options = RequestOptions().circleCrop()
        Glide.with(context).load(url).apply(options).into(iv)
    }

    fun default(context: Context, url: String?, iv: ImageView) {
        val options = RequestOptions().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
        Glide.with(context).load(url).apply(options).into(iv)
    }
}

