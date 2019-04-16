package com.example.githubdemo.net

/**
 * @author 14512 on 2019/4/16
 */
data class ApiResult<T>(var success: Boolean, var code: Int, var message: String, var data: T)