package com.example.hp.rxbusretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void calc(View view) {
        RxBus.getInstance().send(new CalculateEvent("from RxBus：3 * 2 = "+ 3 * 2));
        finish();
    }
}
