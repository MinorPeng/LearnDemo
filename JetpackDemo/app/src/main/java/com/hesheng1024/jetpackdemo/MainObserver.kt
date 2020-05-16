package com.hesheng1024.jetpackdemo

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *
 * @author hesheng1024
 * @date 2020/5/16 10:53
 */
class MainObserver : LifecycleObserver {

    private val TAG = "MainObserver"

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun actionCreate() {
        Log.d(TAG, "create")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun actionStart() {
        Log.d(TAG, "start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun actionResume() {
        Log.d(TAG, "resume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun actionPause() {
        Log.d(TAG, "pause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun actionStop() {
        Log.d(TAG, "stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun actionDestroy() {
        Log.d(TAG, "destroy")
    }

}