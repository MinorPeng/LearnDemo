package com.example.jetpackdemo.network.exception

/**
 * @author 14512 on 2018/3/17
 */
class ServiceException(val code: Int, val msg: String) : RuntimeException() {

}