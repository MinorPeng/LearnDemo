package com.example.a14512.founddemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.a14512.founddemo.R;
import com.example.a14512.founddemo.overlayutil.DrivingRouteOverlay;
import com.example.a14512.founddemo.overlayutil.TransitRouteOverlay;

/**
 * @author 14512 on 2018/1/25
 */

public class ThirdFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ThirdFragment";
    private EditText edtStart;
    private EditText edtEnd;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private RoutePlanSearch mSearch;

    private  PlanNode stMassNode, enMassNode, lastNode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        initView(view);
        search(view);
        return view;
    }

    private void search(View view) {
        mSearch = RoutePlanSearch.newInstance();

        OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener(){

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
                //在公交线路规划回调方法中添加TransitRouteOverlay用于展示换乘信息
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //未找到结果
                    return;
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                Log.e(TAG, " start " + result.error);
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteLine route = result.getRouteLines().get(0);
                    Log.e(TAG, "" + route.getTitle());
                    //创建公交路线规划线路覆盖物

                    TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                    //设置公交路线规划数据
                    overlay.setData(route);
                    //将公交路线规划覆盖物添加到地图中
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
                //获取跨城综合公共交通线路规划结果

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //未找到结果
                    return;
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                Log.e(TAG, " start " + result.error);
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteLine route = result.getRouteLines().get(0);
                    Log.e(TAG, "" + route.getTitle());

                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    overlay.setData(route);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };

        mSearch.setOnGetRoutePlanResultListener(routeListener);

        stMassNode = PlanNode.withCityNameAndPlaceName("重庆", "邮电大学");
        enMassNode = PlanNode.withCityNameAndPlaceName("重庆", "南坪");
        lastNode = PlanNode.withCityNameAndPlaceName("重庆", "解放碑");
    }

    private void initView(View view) {
        edtStart = view.findViewById(R.id.edt_start);
        edtStart.setOnClickListener(this);
        edtEnd = view.findViewById(R.id.edt_end);
        edtEnd.setOnClickListener(this);
        Button btnCar = view.findViewById(R.id.btn_car);
        btnCar.setOnClickListener(this);
        Button btnBus = view.findViewById(R.id.btn_bus);
        btnBus.setOnClickListener(this);
        Button btnWalk = view.findViewById(R.id.btn_walk);
        btnWalk.setOnClickListener(this);
        mMapView = view.findViewById(R.id.texture_map_view_frag_third);
        mBaiduMap = mMapView.getMap();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_car:
                mSearch.drivingSearch(new DrivingRoutePlanOption().from(stMassNode).to(enMassNode));
                break;
            case R.id.btn_bus:
                mSearch.transitSearch(new TransitRoutePlanOption().from(stMassNode).city("重庆").to(enMassNode));
                mSearch.transitSearch(new TransitRoutePlanOption().from(enMassNode).city("重庆").to(lastNode));
                break;
            case R.id.btn_walk:
                mSearch.walkingSearch(new WalkingRoutePlanOption().from(stMassNode).to(enMassNode));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
}
