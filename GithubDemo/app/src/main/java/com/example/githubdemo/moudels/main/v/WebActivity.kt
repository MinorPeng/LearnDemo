package com.example.githubdemo.moudels.main.v

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.example.githubdemo.R
import com.example.githubdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*


/**
 * @author 14512 on 2019/4/17
 */
class WebActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, htmlUrl: String?) {
            context.startActivity(Intent(context, WebActivity::class.java).putExtra("url", htmlUrl))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_web

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        if (intent == null) {
            toastMsg("intent can't be null")
            return
        }

        val url = intent.getStringExtra("url")
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportMultipleWindows(true)
        settings.loadsImagesAutomatically = true
        webView.loadUrl(url)
    }
}