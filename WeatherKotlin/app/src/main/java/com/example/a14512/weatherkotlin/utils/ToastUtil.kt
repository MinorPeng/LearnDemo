package com.example.a14512.weatherkotlin.utils

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.example.a14512.weatherkotlin.WeatherApplication

/**
 * @author 14512 on 2018/3/17
 */
object ToastUtil {
    fun show(content: String) {
        Toast.makeText(WeatherApplication.getContext(), content, Toast.LENGTH_SHORT).show()
    }

    fun show(@StringRes res: Int) {
        Toast.makeText(WeatherApplication.getContext(), res, Toast.LENGTH_SHORT).show()
    }

    fun showLong(content: String) {
        Toast.makeText(WeatherApplication.getContext(), content, Toast.LENGTH_LONG).show()
    }

    fun showLong(@StringRes res: Int) {
        Toast.makeText(WeatherApplication.getContext(), res, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context, content: String) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context, @StringRes res: Int) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
    }

    fun showLong(context: Context, content: String) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show()
    }

    fun showLong(context: Context, @StringRes res: Int) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
    }
}