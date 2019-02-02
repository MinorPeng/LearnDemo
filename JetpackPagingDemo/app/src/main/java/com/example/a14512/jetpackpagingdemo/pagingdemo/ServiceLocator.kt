package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author 14512 on 2018/8/26
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(application = context.applicationContext as Application)
                }
                return instance!!
            }
        }

        /**
         * 允许替换默认的ServiceLocator
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getNetworkExecutor(): Executor

    fun getDiskIOExecutor(): Executor

    fun getApiService(): ApiService

    fun getRepository(): StuDataRepository
}

class DefaultServiceLocator(val application: Application) : ServiceLocator {


    @Suppress("PrivatePropertyName")
    private val DISK_IO = Executors.newSingleThreadExecutor()

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    private val db by lazy {
    }

    private val api by lazy {
    }

    override fun getNetworkExecutor(): Executor = NETWORK_IO

    override fun getDiskIOExecutor(): Executor = DISK_IO

    override fun getApiService(): ApiService = api as ApiService

    override fun getRepository(): StuDataRepository = StuDataRepository(getNetworkExecutor())
}