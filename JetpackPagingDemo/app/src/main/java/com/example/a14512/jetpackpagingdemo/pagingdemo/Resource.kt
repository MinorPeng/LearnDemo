package com.example.a14512.jetpackpagingdemo.pagingdemo

/**
 * @author 14512 on 2018/8/22
 */
data class Resource<out T>(val status: Status, val data: T?, val msg: String?) {
    companion object {
        fun <T> loading(msg: String? = null, data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, msg)
        }

        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String? = null, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }
    }
}


enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}