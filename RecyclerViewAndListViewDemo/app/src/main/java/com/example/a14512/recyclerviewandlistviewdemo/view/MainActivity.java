package com.example.a14512.recyclerviewandlistviewdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a14512.recyclerviewandlistviewdemo.R;

/**
 * @author 14512 by 2018年4月29日
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button btnRecycler = findViewById(R.id.btnRecycler);
        Button btnList = findViewById(R.id.btnList);

        btnRecycler.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRecycler:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.btnList:
                ListViewActivity.actionStart(this);
                break;
            default:
                break;
        }
    }


}
