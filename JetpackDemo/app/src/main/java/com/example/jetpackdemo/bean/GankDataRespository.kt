package com.example.jetpackdemo.bean

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import com.example.jetpackdemo.network.NetManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author phs on 19-2-6
 */
class GankDataRespository private constructor(){
    private val TAG: String = "GankDataRespository"
    private val datas = MediatorLiveData<List<DateGank.DateGankData>>()

    companion object {
        val INSTANCE by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GankDataRespository()
        }
    }

    fun getData(date: String): LiveData<List<DateGank.DateGankData>> {
        var subscriber = object : Observer<DateGank> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                Log.e(TAG, "complete")
            }

            override fun onNext(t: DateGank) {
                Log.e(TAG, t.toString())
                if (t != null) {
                    var list = ArrayList<DateGank.DateGankData>()
                    t.results.android?.let { list.addAll(it) }
                    t.results.app?.let { list.addAll(it) }
                    t.results.ios?.let { list.addAll(it) }
                    t.results.web?.let { list.addAll(it) }
                    t.results.recommend?.let { list.addAll(it) }
                    t.results.restVideo?.let { list.addAll(it) }
                    datas.postValue(list)
                }
            }
        }
        (NetManager.INSTANCE.getDateGank(date)).subscribe(subscriber)
        return datas
    }
}