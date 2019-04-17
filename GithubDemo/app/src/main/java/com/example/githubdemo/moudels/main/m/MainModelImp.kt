package com.example.githubdemo.moudels.main.m

import com.example.githubdemo.moudels.User
import com.example.githubdemo.net.RetrofitManager
import io.reactivex.Observer

/**
 * @author 14512 on 2019/4/16
 */
class MainModelImp : IMainModel {

    override fun getUsersFromNet(userName: String, page: Int, observer: Observer<List<User>>) {
        RetrofitManager.mNetService.getUsers(userName, page).subscribe(observer)
    }

    override fun getUsersFromCache(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setUsersToCache(users: List<User>) {

    }
}