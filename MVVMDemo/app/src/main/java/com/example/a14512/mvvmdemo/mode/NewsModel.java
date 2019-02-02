package com.example.a14512.mvvmdemo.mode;

import com.example.a14512.mvvmdemo.net.RetrofitManager;

import rx.Subscriber;

/**
 * @author 14512 on 2018/8/20
 */
public class NewsModel {
    public void getNews(Subscriber<News> subscriber) {
        RetrofitManager.getInstance().getNews(subscriber);
    }
}
