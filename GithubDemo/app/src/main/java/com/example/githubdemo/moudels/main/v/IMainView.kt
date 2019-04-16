package com.example.githubdemo.moudels.main.v

import com.example.githubdemo.moudels.User

/**
 * @author 14512 on 2019/4/16
 */
interface IMainView {

    fun setAdapter(users: List<User>)

    fun addUsers(users: List<User>)
}