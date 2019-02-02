package com.example.a14512.weatherkotlin.okhttp3

import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * @author 14512 on 2018/3/18
 */
object HttpUtil {
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder().url(address).build()
        client.newCall(request).enqueue(callback)
    }
}