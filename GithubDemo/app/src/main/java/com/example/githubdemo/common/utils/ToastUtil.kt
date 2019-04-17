package com.example.githubdemo.common.utils

import android.content.Context
import android.widget.Toast
import com.example.githubdemo.base.Global

/**
 * @author phs on 19-2-10
 */
object ToastUtil {

    fun show(context: Context, str: String, length: Int) {
        Toast.makeText(context, str, length).show()
    }

    fun showShort(context: Context, str: String) {
        show(context, str, Toast.LENGTH_SHORT)
    }

    fun showShort(str: String) {
        showShort(Global.getContext(), str)
    }

    fun showLong(context: Context, str: String) {
        show(context, str, Toast.LENGTH_LONG)
    }

    fun showLong(str: String) {
        showLong(Global.getContext(), str)
    }
}