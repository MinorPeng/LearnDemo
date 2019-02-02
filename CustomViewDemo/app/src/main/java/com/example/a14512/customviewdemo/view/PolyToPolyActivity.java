package com.example.a14512.customviewdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.a14512.customviewdemo.customview.PolyToPolyView;

/**
 * @author 14512 on 2018/8/21
 */
public class PolyToPolyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PolyToPolyView(this));
    }
}
