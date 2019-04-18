package com.example.githubdemo.moudels.main.m

import com.example.githubdemo.moudels.User
import io.reactivex.Observer

/**
 * @author 14512 on 2019/4/16
 */
interface IMainModel {

    fun getUsersFromNet(userName: String, page: Int, observer: Observer<List<User>>)

    fun setUsersToCache(users: List<User>)

    fun getUsersFromCache(userName: String, page: Int): List<User>?
}