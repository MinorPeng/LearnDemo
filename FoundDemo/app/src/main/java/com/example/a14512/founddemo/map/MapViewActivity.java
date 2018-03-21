package com.example.a14512.founddemo.map;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteNode;
import com.example.a14512.founddemo.R;

/**
 * @author 14512 on 2018/1/25
 */

public class MapViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MapViewActivity";

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private boolean isFirstLocate = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        initView();
//        locationSettings();
        routeLine();
    }

    private void routeLine() {
        //起点-重庆
        RouteNode myP1 = new RouteNode();
        RouteNode myP2 = new RouteNode();
        RouteNode myP3 = new RouteNode();
        myP1.setLocation(new LatLng(106.521436,29.532288));
        myP2.setLocation(new LatLng(108.983569,34.285675));
        myP3.setLocation(new LatLng(116.404449,39.920423));
    }

    private void locationSettings() {
        initLocation();
    }

    private void initLocation() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //MapStatusUpdateFactory.zoomTo(20) 就是设置缩放等级的，   20--10m  19--20m
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));

        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerNotifyLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认高精度, Hight_Accuracy：高精度；Battery_Saving：低功耗；Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
//        option.setOpenGps(true);

        //bd09ll：百度经纬度坐标；
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms, 如果设置为0，则代表单次定位，即仅定位一次，默认为0, 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(2000);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
//        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //获取当前位置的详细地址信息
        option.setIsNeedAddress(true);

        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        option.setIsNeedLocationDescribe(true);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
        mLocationClient.start();

    }

    private void initView() {
        Button btnNormal = findViewById(R.id.btn_normal);
        Button btnSatellite = findViewById(R.id.btn_satellite);
        Button btnTraffic = findViewById(R.id.btn_traffic);
        Button btnHeat = findViewById(R.id.btn_heat);

        mMapView = findViewById(R.id.texture_map_view);
        mBaiduMap = mMapView.getMap();

        btnNormal.setOnClickListener(this);
        btnSatellite.setOnClickListener(this);
        btnTraffic.setOnClickListener(this);
        btnHeat.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();  //停止定位
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        // 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                //普通地图 ,mBaiduMap是地图控制器对象
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btn_satellite:
                //卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btn_traffic:
                //开启交通图
                mBaiduMap.setTrafficEnabled(true);
                break;
            case R.id.btn_heat:
                //开启热力图
                mBaiduMap.setBaiduHeatMapEnabled(true);
                break;
            default:
                break;
        }
    }

    //监听器
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //回调接口处理
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                //移动到所在地
                navigateTo(bdLocation);
            }
        }

    }

    private void navigateTo(BDLocation location) {
        Log.e(TAG, location.getAddrStr() + "\n" +location.getLocationDescribe());
        if (isFirstLocate) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        //将自己的位置展示出来
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

       /* //定位跟随态
        MyLocationConfiguration.LocationMode currentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        //默认为 LocationMode.NORMAL 普通态
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        //定位罗盘态
//        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
        BitmapDescriptor currentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_focuse_mark);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(currentMode, true, currentMarker);
        mBaiduMap.setMyLocationConfiguration(config);*/

        showMsg(location);
    }

    private void showMsg(BDLocation location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        addMarker(latitude, longitude);

        addText(latitude, longitude);

//        popWindow(latitude, longitude);

//        addView();
    }

    private void addMarker(double latitude, double longitude) {
        //定义Maker坐标点

        LatLng point = new LatLng(latitude, longitude);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_openmap_focuse_mark);

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                //是否开启近大远小效果
                .perspective(true)
                //透明度
//                .alpha(0.2f)
                //设置手势拖拽
                .draggable(true)
                .position(point)
                .icon(bitmap)
                .extraInfo(bundle);

        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);

        listener();


        //创建OverlayOptions的集合

      /* //批量添加
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        //设置坐标点

        LatLng point1 = new LatLng(39.92235, 116.380338);
        LatLng point2 = new LatLng(39.947246, 116.414977);

        //创建OverlayOptions属性

        OverlayOptions option1 =  new MarkerOptions()
                .position(point1)
                .icon(bdA);
        OverlayOptions option2 =  new MarkerOptions()
                .position(point2)
                .icon(bdA);
        //将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        //在地图上批量添加
        mBaiduMap.addOverlays(options);*/
    }

    private void listener() {
        /*//调用BaiduMap对象的setOnMarkerDragListener方法设置Marker拖拽的监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                //拖拽中
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.e(TAG, marker.getPosition().toString());
                //拖拽结束
            }
            @Override
            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });*/

        mBaiduMap.setOnMarkerClickListener(marker -> {
            popWindow(marker.getExtraInfo().getDouble("latitude"),
                    marker.getExtraInfo().getDouble("longitude"));
            return false;
        });
    }

    private void addText(double latitude, double longitude) {
        //定义文字所显示的坐标点
        LatLng llText = new LatLng(latitude, longitude);

        //构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(24)
                .fontColor(0xFFFF00FF)
                .text("baidu map")
                .rotate(-30)
                .position(llText);

        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);
    }

    private void popWindow(double latitude, double longitude) {
        //创建InfoWindow展示的view
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.mipmap.ic_launcher_round);
        button.setText("this is a window");

        //定义用于显示该InfoWindow的坐标点
        LatLng pt = new LatLng(latitude, longitude);

        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);

        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    private void addView() {
        mBaiduMap.setPadding(0, 0, 0, 200);

        TextView textView = new TextView(this);
        textView.setText("这是用户自定义的View,这个时候logo和底部的一些内容会向上移动，因为MapView设置了底部Padding");
        textView.setBackgroundResource(R.color.colorPrimary); //创建一个TextView然后往底部放置，官方Demo如是操作
        MapViewLayoutParams.Builder builder = new MapViewLayoutParams.Builder();
        builder.layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode);
        builder.width(mMapView.getWidth());
        builder.height(200);
        builder.point(new Point(0, mMapView.getHeight()));
        builder.align(MapViewLayoutParams.ALIGN_LEFT, MapViewLayoutParams.ALIGN_BOTTOM);
        mMapView.addView(textView, builder.build());
    }


}
