package com.example.githubdemo.base

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar

/**
 * @author 14512 on 2019/4/16
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    protected abstract fun initView()

    protected abstract fun getLayoutId(): Int

    /**
     * 获取状态栏高度
     */
    protected fun getStatusHeight(): Int {
        var statusBarHeight = -1
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /*--------------------------------------dialog------------------------------------------------*/
    private var mProgressBar: ProgressBar? = null
    private var mDialog: AlertDialog? = null

    protected fun loadingDialog(msg: String) {
        mProgressBar = ProgressBar(this)
        mDialog = AlertDialog.Builder(this).setView(mProgressBar)?.setTitle(msg)
            ?.setNegativeButton("取消") { dialog, which ->
                dialog.cancel()
            }?.create()
        mDialog?.setCanceledOnTouchOutside(false)
        mDialog?.show()
    }

    protected fun dismissDialog() {
        mDialog?.dismiss()
    }
}