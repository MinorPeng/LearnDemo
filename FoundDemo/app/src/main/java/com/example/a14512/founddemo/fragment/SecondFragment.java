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
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.a14512.founddemo.R;

import java.util.List;

/**
 * @author 14512 on 2018/1/25
 */

public class SecondFragment extends Fragment {
    private static final String TAG = "SecondFragment";

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private PoiSearch mPoiSearch;
    private SuggestionSearch mSuggestionSearch;

    private EditText mEdtStart, mEdtEnd;
    private Button mBtnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        initView(view);
        poiSearch(view);
        suggestionPoi(view);
        return view;
    }

    private void suggestionPoi(View view) {
        mSuggestionSearch = SuggestionSearch.newInstance();

        OnGetSuggestionResultListener listener = res -> {

            if (res == null || res.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            }

            List<SuggestionResult.SuggestionInfo> list = res.getAllSuggestions();
            for (SuggestionResult.SuggestionInfo suggestionInfo : list) {
                Log.e(TAG, "热搜" + suggestionInfo.city + " " +
                        suggestionInfo.district + ": " + suggestionInfo.key);
            }
            //获取在线建议检索结果
        };

        mSuggestionSearch.setOnGetSuggestionResultListener(listener);

        // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新

        mBtnSearch.setOnClickListener(v ->
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(mEdtEnd.getText().toString())
                        .city(mEdtStart.getText().toString())));

    }

    private void poiSearch(View view) {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                Log.e(TAG, "poi" + poiResult);
                if (poiResult != null && poiResult.getAllAddr() != null && poiResult.getAllPoi()!= null) {
                    Log.e(TAG, poiResult.getAllAddr().get(0).name +
                            "  " + poiResult.getAllAddr().get(0).address + "\n"
                            + poiResult.getAllPoi().get(0).city + "  "
                            + poiResult.getAllPoi().get(0).name + "  "
                            + poiResult.getAllPoi().get(0).address);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                //获取Place详情页检索结果
                Log.e(TAG, "detail: " + poiDetailResult.getDetailUrl());
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };

        //设置POI检索监听者
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("重庆")
                .keyword("美食")
                .pageNum(10));
    }

    private void initView(View view) {
        mEdtStart = view.findViewById(R.id.edt_start);
        mEdtEnd = view.findViewById(R.id.edt_end);
        mBtnSearch = view.findViewById(R.id.btn_search);
        mMapView = view.findViewById(R.id.texture_map_view_frag);
        mBaiduMap = mMapView.getMap();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
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
