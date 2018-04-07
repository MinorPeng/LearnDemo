package com.example.a14512.fromsmalltospecialist.fourth;

import android.support.constraint.solver.Cache;
import android.util.Log;

import com.example.a14512.fromsmalltospecialist.fourth.base.Request;
import com.example.a14512.fromsmalltospecialist.fourth.base.Response;
import com.example.a14512.fromsmalltospecialist.fourth.utils.LruMemCache;

import java.util.concurrent.BlockingQueue;

/**
 * @author 14512 on 2018/4/7
 */

public class NetworkExecutor extends Thread {

    /**
     * 网络请求队列
     */
    private BlockingQueue<Request<?>> mBlockingQueue;

    /**
     * 网络请求栈
     */
    private HttpStack mHttpStack;

    /**
     * 结果分发器，将结构投递到主线程
     */
    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();

    /**
     * 请求缓存
     */
    private static Cache<String, Response> mReqCache = new LruMemCache();

    /**
     * 是否停止
     */
    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue<Request<?>> blockingQueue, HttpStack httpStack) {
        this.mBlockingQueue = blockingQueue;
        this.mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                final Request<?> request = mBlockingQueue.take();
                if (request.isCancel()) {
                    Log.d("###", "### cancel");
                    continue;
                }
                Response response = null;
                if (isUserCache(request)) {
                    //从缓存中取
                    response = mReqCache.get(request.getUrl());
                } else {
                    //网络上获取
                    response = mHttpStack.performRequest(request);
                    //如果该请求需要缓存，那么请求成功则缓存到mResponseCache
                    if (request.isShouldCache() && isSuccess(response)) {
                        mReqCache.put(request.getUrl(), response);
                    }
                }
                //分发结果
                mResponseDelivery.deliveryResponse(request, response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("", "### dispatcher quit");
        }
    }

    private boolean isSuccess(Response response) {
        return response != null && response.getStatusCode() == 200;
    }

    private boolean isUserCache(Request<?> request) {
        return request.isShouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit() {
        isStop = true;
        interrupt();
    }
}
