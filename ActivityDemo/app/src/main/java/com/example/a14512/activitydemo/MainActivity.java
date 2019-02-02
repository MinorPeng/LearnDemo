package com.example.a14512.activitydemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a14512.activitydemo.utils.LogUtil;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity {
    private Button mBtnAdd, mBtnRemove, mBtnHide, mBtnReplace, mBtnShow;
    private Fragment1 fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("onCreate");
        setContentView(R.layout.activity_main);
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnRemove = findViewById(R.id.btnRemove);
        mBtnHide = findViewById(R.id.btnHide);
        mBtnReplace = findViewById(R.id.btnReplace);
        mBtnShow = findViewById(R.id.btnShow);

        fragment1 = new Fragment1();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, fragment1);
        transaction.commit();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, fragment1);
                transaction.commit();
            }
        });

        mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment1);
                transaction.commit();
            }
        });

        mBtnReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.frameLayout, fragment2);
                transaction.commit();
            }
        });

        mBtnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(fragment1);
                transaction.commit();
            }
        });

        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(fragment1);
                transaction.commit();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d("onRestoreInstanceState");
    }
}
