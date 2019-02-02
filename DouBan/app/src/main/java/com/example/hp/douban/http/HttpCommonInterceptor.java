package com.example.hp.douban.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HP on 2017/5/11.
 */

public class HttpCommonInterceptor implements Interceptor {

    private Map<String, String> mHeaderPramasMap = new HashMap<>();
    public HttpCommonInterceptor() {

    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        //添加新的参数，添加到url中
         /* HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());*/

        // 新的请求

        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());
        //添加公共参数，添加到header中
        if (mHeaderPramasMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderPramasMap.entrySet()) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }

        Request newRequest = requestBuilder.build();

        return chain.proceed(newRequest);
    }

    public static class Builder{
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderPramasMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public HttpCommonInterceptor build() {
            return mHttpCommonInterceptor;
        }
    }
}
