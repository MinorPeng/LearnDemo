package com.example.a14512.founddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.a14512.founddemo.R;

/**
 * @author 14512 on 2018/1/25
 */

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.e(TAG, name);
        iniView();
    }

    private void iniView() {
        TextView tvItem = findViewById(R.id.tv_item_name);
        if (name != null) {
            tvItem.setText(name);
        }
    }
}
