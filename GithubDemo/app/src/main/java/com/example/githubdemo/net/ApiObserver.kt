package com.example.githubdemo.net

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.ProgressBar
import com.example.githubdemo.common.utils.LogUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author 14512 on 2019/3/12
 */
abstract class ApiObserver<T> : Observer<T> {
    private var mProgressBar: ProgressBar? = null
    private var mContext: Context? = null
    private var mDialog: AlertDialog? = null

    constructor(isShowProgress: Boolean, msg: String, context: Context) {
        if (isShowProgress) {
            mContext = context
            mProgressBar = ProgressBar(mContext)
            mDialog = AlertDialog.Builder(mContext!!).setView(mProgressBar)?.setTitle(msg)
                ?.setNegativeButton("取消") { dialog, which ->
                    dialog.cancel()
                }?.create()
            mDialog?.setCanceledOnTouchOutside(false)
            mDialog?.show()
        }
    }

    constructor()

    override fun onComplete() {
        mDialog?.dismiss()
        LogUtil.d(javaClass.name, "onComplete")
    }

    override fun onSubscribe(d: Disposable) {
        LogUtil.d(javaClass.name, "onSubscribe")
    }

    override fun onNext(t: T) {
        mDialog?.dismiss()
    }

    override fun onError(e: Throwable) {
        mDialog?.dismiss()
    }

}