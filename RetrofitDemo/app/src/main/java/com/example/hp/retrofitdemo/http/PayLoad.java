package com.example.hp.retrofitdemo.http;

import rx.functions.Func1;

/**
 * Created by HP on 2017/4/9.
 */

public class PayLoad<T> implements Func1<BaseResponse<T>, T> {
    @Override
    public T call(BaseResponse<T> tBaseResponse) {
        //获取数据失败时，包装一个Fault 抛给上层处理错误
        if (!tBaseResponse.isSuccess()) {
            throw new Fault(tBaseResponse.status, tBaseResponse.message);
        }
        return tBaseResponse.data;
    }
}
