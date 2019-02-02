package com.example.a14512.mvvmdemo.viewmodel;

import android.app.Activity;

import com.example.a14512.mvvmdemo.databinding.ActivityMainBinding;
import com.example.a14512.mvvmdemo.mode.News;
import com.example.a14512.mvvmdemo.mode.NewsModel;
import com.example.a14512.mvvmdemo.mode.NewslistBean;

import rx.Subscriber;

/**
 * @author 14512 on 2018/8/20
 */
public class ViewModel {

    public ActivityMainBinding activityMainBinding;

    public NewslistBean news;

    public NewsModel model;

    private int num=1;

    public ViewModel(final ActivityMainBinding binding) {
        this.activityMainBinding = binding;

        model = new NewsModel();
        model.getNews(new Subscriber<News>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                news=new NewslistBean("error","error","error","error","error");
                activityMainBinding.setNews(news);
            }

            @Override
            public void onNext(News news) {
                activityMainBinding.setNews(news.getNewslist().get(0));
            }
        });
    }
}
