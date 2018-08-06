package com.example.a14512.activitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a14512.activitydemo.utils.LogUtil;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this.toString() + "taskId " + getTaskId());
        setContentView(R.layout.activity_third);

        Button button = findViewById(R.id.btnThird);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdActivity.this, FourthActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("onRestart " + this.toString() + " taskId " + getTaskId());
    }
}
