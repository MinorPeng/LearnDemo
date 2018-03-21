package com.example.hp.mvp_rx_retrofit.base;

/**
 * 描述：请求数据的回调接口
 * Presenter用于接受model获取（加载）数据后的回调
 *
 * Created by HP on 2017/5/4.
 */

public interface IBaseRequestCallBack<T> {
    /**
     * @descriptoin	请求之前的操作
     */
    void beforeRequest();

    /**
     * @descriptoin	请求异常
     * @param throwable 异常类型
     */
    void requestError(Throwable throwable);

    /**
     * @descriptoin	请求完成
     */
    void requestComplete();

    /**
     * @descriptoin	请求成功
     * @param callBack 根据业务返回相应的数据
     */
    void requestSuccess(T callBack);
}
