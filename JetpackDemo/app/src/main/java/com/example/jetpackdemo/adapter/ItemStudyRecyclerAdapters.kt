package com.example.jetpackdemo.adapter

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jetpackdemo.R

/**
 * @author phs on 19-2-7
 * itemStudyRecycler的binding adapter
 */
@BindingAdapter("imgFromUrl")
fun bindImgFromUrl(view: ImageView, imgUrl: String?) {
    val options = RequestOptions()
        .error(R.mipmap.iv_load_failure)
        .placeholder(R.mipmap.iv_load_failure)
        .circleCrop()
    Glide.with(view.context).load(imgUrl).apply(options).into(view)
}

@BindingAdapter("isGone")
fun bindIsGone(view: TextView, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}