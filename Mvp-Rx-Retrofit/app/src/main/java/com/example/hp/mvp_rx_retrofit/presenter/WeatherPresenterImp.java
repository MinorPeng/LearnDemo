package com.example.hp.mvp_rx_retrofit.presenter;

import android.content.Context;

import com.example.hp.mvp_rx_retrofit.base.BasePresenterImp;
import com.example.hp.mvp_rx_retrofit.bean.WeatherInfoBean;
import com.example.hp.mvp_rx_retrofit.model.WeatherModelImp;
import com.example.hp.mvp_rx_retrofit.view.WeatherView;

/**
 * 描述：MVP中的P实现类
 * Created by HP on 2017/5/4.
 */

public class WeatherPresenterImp extends BasePresenterImp<WeatherView,WeatherInfoBean>
        implements WeatherPresenter{//传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁

    private Context context = null;
    private WeatherModelImp weatherModelImp = null;

    /**
     * @descriptoin 构造方法
     * @param view 具体业务的视图接口对象
     */
    public WeatherPresenterImp(WeatherView view, Context context) {
        super(view);
        this.context = context;
        this.weatherModelImp = new WeatherModelImp(context);
    }

    @Override
    public void loadWeather(String key, String city) {
        weatherModelImp.loadWeather(key, city, this);
    }

    @Override
    public void unSubscribe() {
        weatherModelImp.onUnsubscribe();
    }

}
