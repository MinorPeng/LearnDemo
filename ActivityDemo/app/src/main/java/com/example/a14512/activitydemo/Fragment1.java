package com.example.a14512.activitydemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a14512.activitydemo.utils.LogUtil;

/**
 * @author 14512 on 2018/7/31
 */
public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d("onCreateView");
        View view = inflater.inflate(R.layout.fragment1, container, false);
        Button button = view.findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SecondActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d("onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("onDestroyVIew");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("onDetach");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.d("onViewStateRestored");
    }
}
