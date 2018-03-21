package com.example.a14512.ipcdemo.more_process;

import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a14512.ipcdemo.R;

/**
 * @author 14512
 * */

public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.e(TAG, Process.myPid() + ":" + UserManager.sUserId);
    }
}
