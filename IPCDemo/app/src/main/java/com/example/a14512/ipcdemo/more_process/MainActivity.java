package com.example.a14512.ipcdemo.more_process;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a14512.ipcdemo.R;

/**
 * @author 14512
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mBtSecondAc;
    private Button mBtThirdAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.e(TAG, Process.myPid() + ":" + UserManager.sUserId);
    }

    private void initView() {
        mBtSecondAc = findViewById(R.id.button);
        mBtThirdAc = findViewById(R.id.button2);

        mBtSecondAc.setOnClickListener(this);
        mBtThirdAc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, ThirdActivity.class));
                break;
            default:
                break;
        }
    }
}
