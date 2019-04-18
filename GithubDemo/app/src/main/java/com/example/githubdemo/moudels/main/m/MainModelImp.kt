package com.example.githubdemo.moudels.main.m

import com.example.githubdemo.moudels.User
import com.example.githubdemo.moudels.business.Business
import com.example.githubdemo.net.RetrofitManager
import io.reactivex.Observer

/**
 * @author 14512 on 2019/4/16
 */
class MainModelImp : IMainModel {

    override fun getUsersFromNet(userName: String, page: Int, observer: Observer<List<User>>) {
        RetrofitManager.mNetService.getUsers(userName, page).subscribe(observer)
    }

    override fun getUsersFromCache(userName: String, page: Int): List<User>? {
        return Business.userRespository.getUsers(userName, page)
    }

    override fun setUsersToCache(users: List<User>) {

    }
}