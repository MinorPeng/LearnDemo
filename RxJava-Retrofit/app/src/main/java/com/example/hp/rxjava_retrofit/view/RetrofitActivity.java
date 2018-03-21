package com.example.hp.rxjava_retrofit.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.rxjava_retrofit.R;
import com.example.hp.rxjava_retrofit.base.BaseActivity;
import com.example.hp.rxjava_retrofit.bean.MovieBean;
import com.example.hp.rxjava_retrofit.bus.RefreshMessage;
import com.example.hp.rxjava_retrofit.bus.RxBus;
import com.example.hp.rxjava_retrofit.service.BaseUrl;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;

public class RetrofitActivity extends BaseActivity {

    @Bind(R.id.retrofit)
    Button retrofit;
    @Bind(R.id.retrofitrx)
    Button retrofitrx;
    @Bind(R.id.rxbus)
    Button rxbus;
    private Subscriber<MovieBean> subscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }


    private void getMovieByRx() {
        subscriber = new Subscriber<MovieBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                //subscriber.unsubscribe();//取消一个网络请求
                Toast.makeText(RetrofitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(MovieBean movieBean) {
                Log.e("content", movieBean.getTitle());
            }
        };
        BaseUrl.getInstance().getTopMovie(subscriber, 0, 10);
    }

    @OnClick({R.id.retrofit, R.id.retrofitrx, R.id.rxbus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.retrofit:
                //图片
                uploadPhoto();
                break;
            case R.id.retrofitrx:
                getMovieByRx();
                break;
            case R.id.rxbus:
                RxBus.getInstance().post(new RefreshMessage("mingzi", "333"));
                break;
        }
    }

    private void uploadPhoto() {
        subscriber = new Subscriber<MovieBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                //subscriber.unsubscribe();//取消一个网络请求
                Toast.makeText(RetrofitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(MovieBean movieBean) {
                Log.e("content", movieBean.getTitle());
            }
        };
        BaseUrl.getInstance().getTopMovie(subscriber, 0, 10);
    }
}
