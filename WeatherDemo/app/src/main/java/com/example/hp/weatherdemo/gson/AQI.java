package com.example.hp.weatherdemo.gson;

/**
 * Created by HP on 2017/2/10.
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
