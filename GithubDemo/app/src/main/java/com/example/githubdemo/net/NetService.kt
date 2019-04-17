package com.example.githubdemo.net

import com.example.githubdemo.moudels.User
import io.reactivex.Observable

/**
 * @author 14512 on 2019/4/16
 */
class NetService(private val apiService: ApiService) {
    fun getUsers(userName: String, page: Int): Observable<List<User>> {
        return apiService.getUser(userName, page)
            .compose(SchedulersTransform.transformMain())
    }
}