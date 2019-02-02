package com.example.a14512.mvvmdemo.net;

import android.util.Log;

import com.example.a14512.mvvmdemo.mode.News;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 14512 on 2018/8/20
 */
public class RetrofitManager {
    private static final String TAG = "RetrofitManager";

    private static final int TIME_OUT = 5;
    private Retrofit mRetrofit;
    private ApiService mApiService;

    private RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiService.BASE_URL)
                .build();
        mApiService = mRetrofit.create(ApiService.class);

    }

    private static class SingleHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                //这里获取请求返回的cookie
                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    if(originalResponse.headers("Set-Cookie").size()==2){
                        CookieUtils.COOKIE = originalResponse.headers("Set-Cookie").get(1);
                        Log.e(TAG, "intercept: 保存cookie"+CookieUtils.COOKIE);
                    }
                }
                return originalResponse;
            }
        });
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                if(null!=CookieUtils.COOKIE){
                    builder.addHeader("Cookie", CookieUtils.COOKIE);
                    Log.e(TAG, "intercept: 添加cookie"+ CookieUtils.COOKIE);
                }else{
                    Log.e(TAG, "intercept: 添加空cookie" );
                }

                return chain.proceed(builder.build());
            }
        });

        return okHttpClient;
    }

    private class HttpResultFunc<T> implements Func1<Result<T>, T> {

        @Override
        public T call(Result<T> tHttpResult) {
            if (tHttpResult.getErrorCode().equals("0")) {
                throw new PreDealException(tHttpResult.getErrorMsg());
            }
            return tHttpResult.getData();
        }
    }

    public void getNews(Subscriber<News> subscriber){
        mApiService.getNews("4a0090627cf07a50def18da875165740","10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
