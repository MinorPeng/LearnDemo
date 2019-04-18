package com.example.githubdemo.moudels.main.p

import android.content.Context
import com.example.githubdemo.common.utils.LogUtil
import com.example.githubdemo.moudels.User
import com.example.githubdemo.moudels.business.Business
import com.example.githubdemo.moudels.main.m.MainModelImp
import com.example.githubdemo.moudels.main.v.IMainView
import com.example.githubdemo.net.ApiObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author 14512 on 2019/4/16
 */
class MainPresenter(private val mView: IMainView, private val mContext: Context) {
    private val mModel = MainModelImp()

    fun searchUser(userName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val users = Business.userRespository.getUsers(userName, 1)
            GlobalScope.launch(Dispatchers.Main) {
                if (users.isNullOrEmpty()) {
                    mModel.getUsersFromNet(userName = userName, page = 1, observer = object : ApiObserver<List<User>>() {
                        override fun onNext(t: List<User>) {
                            if (t.isNullOrEmpty()) {
                                mView.toastMsg("data is null")
                            } else {
                                mView.setAdapter(t)
                                GlobalScope.launch(Dispatchers.IO) {
                                    Business.userRespository.insertUser(t)
                                }
                            }
                        }

                        override fun onError(e: Throwable) {
                            mView.toastMsg("something error ${e.printStackTrace()}")
                        }
                    })
                } else {
                    mView.setAdapter(users)
                }
            }
        }
    }

    fun getUserByPage(userName: String, page: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val users = Business.userRespository.getUsers(userName, page)
            GlobalScope.launch(Dispatchers.Main) {
                if (users.isNullOrEmpty()) {
                    mModel.getUsersFromNet(userName, page, object : ApiObserver<List<User>>() {
                        override fun onNext(t: List<User>) {
                            LogUtil.e(t.size.toString())
                            if (t.isNullOrEmpty()) {
                                mView.showFooterView()
                            } else {
                                mView.addUsers(t)
                                GlobalScope.launch(Dispatchers.IO) {
                                    Business.userRespository.insertUser(t)
                                }
                            }
                        }

                        override fun onError(e: Throwable) {
                            mView.toastMsg("something error ${e.printStackTrace()}")
                        }
                    })
                } else {
                    mView.setAdapter(users)
                }
            }
        }
    }
}