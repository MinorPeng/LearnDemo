package com.example.githubdemo.moudels.main.v

import com.example.githubdemo.R
import com.example.githubdemo.base.BaseActivity
import com.example.githubdemo.moudels.main.p.MainPresenter

class MainActivity : BaseActivity() {
    private var mPresenter: MainPresenter? = null

    override fun initView() {

    }

    override fun getLayoutId(): Int = R.layout.activity_main


}
