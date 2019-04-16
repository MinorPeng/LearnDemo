package com.example.githubdemo.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * @author 14512 on 2019/3/4
 */
object GlideUtil {

    fun portrait(context: Context, url: String, iv: ImageView) {
        LogUtil.e(javaClass.name, "portrait $url")
        val options = RequestOptions().circleCrop()
            /*.error(com.canyuncloud.pad.R.drawable.portrait)
            .placeholder(com.canyuncloud.pad.R.drawable.portrait)*/
            .circleCrop()
        Glide.with(context).load(url).apply(options).into(iv)
    }

    fun circle(context: Context, url: String, iv: ImageView) {
        val options = RequestOptions().circleCrop()
        Glide.with(context).load(url).apply(options).into(iv)
    }

    fun default(context: Context, url: String, iv: ImageView) {
        val options = RequestOptions()
        Glide.with(context).load(url).apply(options).into(iv)
    }
}

