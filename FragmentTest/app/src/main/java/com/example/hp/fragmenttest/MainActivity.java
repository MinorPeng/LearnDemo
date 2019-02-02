package com.example.hp.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        //replaceFragment(new RightFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                replaceFragment(new AnotherRightFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();  //获取FragmentManager，通过调用getSupportFragmentManager
        //FragmentTransaction transaction = fragmentManager.beginTransaction();  //开启一个事务
        //transaction.replace(R.id.right_layout, fragment);  //添加或替换碎片
        //transaction.addToBackStack(null);
        //        transaction.commit();  //提交事务
    }
}