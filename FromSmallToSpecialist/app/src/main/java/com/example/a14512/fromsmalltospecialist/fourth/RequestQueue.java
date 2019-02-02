package com.example.a14512.fromsmalltospecialist.fourth;

import android.util.Log;

import com.example.a14512.fromsmalltospecialist.fourth.base.Request;
import com.example.a14512.fromsmalltospecialist.fourth.imp.HttpStack;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 14512 on 2018/4/7
 */

public final class RequestQueue {

    /**
     * 线程安全的请求队列
     */
    private BlockingQueue<Request<?>> mRequestBlockingQueue = new PriorityBlockingQueue<>();

    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    /**
     * 默认核心数，CPU+1
     */
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * CPU核心数 + 1 个分发线程数
     */
    private int mDispatcherNums = DEFAULT_CORE_NUMS;

    /**
     * 执行网络请求的线程
     */
    private NetworkExecutor[] mDispatchers = null;

    /**
     * 网络请求的真正执行者
     */
    private HttpStack mHttpStack;

    protected RequestQueue(int coreNum, HttpStack httpStack) {
        this.mDispatcherNums = coreNum;
        this.mHttpStack = httpStack;
    }

    /**
     * 启动NetworkExecutor
     */
    private final void startNetworkWxrcutors() {
        mDispatchers = new NetworkExecutor[mDispatcherNums];
        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetworkExecutor(mRequestBlockingQueue, mHttpStack);
            mDispatchers[i].start();
        }
    }

    public void start() {
        stop();
        startNetworkWxrcutors();
    }

    /**
     * 停止NetworkExecutor
     */
    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].quit();
            }
        }
    }


    /**
     * 添加到队列中
     * @param request
     */
    public void addRequest(Request<?> request) {
        if (!mRequestBlockingQueue.contains(request)) {
            request.setSerialNum(this.generateSerialNumber());
        } else {
            Log.d("", "### request is exited!");
        }
    }

    /**
     * @return 为每个请求生成一个序列号
     */
    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }

}
