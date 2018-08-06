package com.example.a14512.jetpackpagingdemo

/**
 * @author 14512 on 2018/7/7
 */
class Result<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(msg: String? = null, data: T? = null): Result<T> {
            return Result(Status.LOADING, data, msg)
        }
        fun <T> success(data: T? = null): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }
        fun <T> error(msg: String? = null, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}