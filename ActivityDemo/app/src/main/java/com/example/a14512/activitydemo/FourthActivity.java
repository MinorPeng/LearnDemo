package com.example.a14512.activitydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a14512.activitydemo.utils.LogUtil;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this.toString() + "taskId " + getTaskId());
        setContentView(R.layout.activity_fourth);
        Button button = findViewById(R.id.btnFourth);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FourthActivity.this, ThirdActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("destroy " + this.toString() + " taskId " + getTaskId());
    }
}
