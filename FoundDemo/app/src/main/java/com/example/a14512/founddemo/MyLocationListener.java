package com.example.a14512.founddemo;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

/**
 * @author 14512 on 2018/1/25
 */

public class MyLocationListener extends BDAbstractLocationListener {
    private static final String TAG = "MyLocationListener";

    private double latitude;
    private double longitude;
    private float radius;
    private String coorType;
    private int errorCode;

    @Override
    public void onReceiveLocation(BDLocation location) {
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        Log.e(TAG, location.getCoorType() + "  " + location.getLatitude() + " " + location.getLongitude());
        //获取纬度信息
        latitude = location.getLatitude();
        //获取经度信息
        longitude = location.getLongitude();
        //获取定位精度，默认值为0.0f
        radius = location.getRadius();

        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        coorType = location.getCoorType();

        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        errorCode = location.getLocType();

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getRadius() {
        return radius;
    }

    public String getCoorType() {
        return coorType;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
