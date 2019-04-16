package com.example.githubdemo.common.utils

import android.util.Log

object LogUtil {
    val ASSERT = 7
    val DEBUG = 3
    val ERROR = 6
    val INFO = 4
    val VERBOSE = 2
    val WARN = 5

    fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        Log.v(tag, msg, tr)
    }

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        Log.d(tag, msg, tr)
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        Log.i(tag, msg, tr)
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    fun w(tag: String, msg: String, tr: Throwable?) {
        Log.i(tag, msg, tr)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun e(tag: String, msg: String, tr: Throwable?){
        Log.e(tag, msg, tr)
    }

    fun v(msg: String) {
        v(getClassName(), msg)
    }

    fun d(msg: String) {
        d(getClassName(), msg)
    }

    fun i(msg: String) {
        i(getClassName(), msg)
    }

    fun w(msg: String) {
        w(getClassName(), msg)
    }

    fun e(msg: String) {
        e(getClassName(), msg)
    }

    /**
     * @return 当前的类名(simpleName, 此时运行的类名)
     */
    private fun getClassName(): String {
        var result: String
        val thisMethodStack = Thread.currentThread().stackTrace[2]
        result = thisMethodStack.className
        val lastIndex = result.lastIndexOf(".")
        result = result.substring(lastIndex + 1)

        val i = result.indexOf("$")// 剔除匿名内部类名
        return if (i == -1) result else result.substring(0, i)
    }
}