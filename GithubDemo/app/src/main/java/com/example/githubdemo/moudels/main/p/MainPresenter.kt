package com.example.githubdemo.moudels.main.p

import android.content.Context
import com.example.githubdemo.common.utils.LogUtil
import com.example.githubdemo.moudels.User
import com.example.githubdemo.moudels.main.m.MainModelImp
import com.example.githubdemo.moudels.main.v.IMainView
import com.example.githubdemo.net.ApiObserver

/**
 * @author 14512 on 2019/4/16
 */
class MainPresenter(private val mView: IMainView, private val mContext: Context) {
    private val mModel = MainModelImp()

    fun searchUser(userName: String) {
        mModel.getUsersFromNet(userName = userName, page = 1, observer = object : ApiObserver<List<User>>() {
            override fun onNext(t: List<User>) {
                LogUtil.e(this@MainPresenter.javaClass.name, t.size.toString())
                mView.setAdapter(t)
            }

            override fun onError(e: Throwable) {
                mView.toastMsg("something error ${e.printStackTrace()}")
            }
        })
    }

    fun getUserByPage(userName: String, page: Int) {
        mModel.getUsersFromNet(userName, page, object : ApiObserver<List<User>>() {
            override fun onNext(t: List<User>) {
                LogUtil.e(t.size.toString())
                if (t.isNullOrEmpty()) {
                    mView.showFooterView()
                } else {
                    mView.addUsers(t)
                }
            }

            override fun onError(e: Throwable) {
                mView.toastMsg("something error ${e.printStackTrace()}")
            }
        })
    }

}