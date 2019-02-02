package com.example.hp.fragpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> viewList;
    private ViewsPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.frags_page, null);
            TextView textView = (TextView) view.findViewById(R.id.num_text);
            textView.setText(i + "page");
            viewList.add(view);
        }
        adapter = new ViewsPagerAdapter(viewList);
        viewPager.setAdapter(adapter);*/
        initVIew();
    }

    private void initVIew() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        for (int i=1; i<4;i++) {
            Fragments frag = new Fragments();
//            TextView text = (TextView) findViewById(R.id.num_text);
//            text.setText(i+"page");
            fragmentList.add(frag);
        }

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);

    }

}
