package com.example.hp.douban.modules.main.view;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.douban.R;
import com.example.hp.douban.modules.main.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.myViewPager)
    ViewPager mViewPager;  //要使用的ViewPager
    @Bind(R.id.movie_text)
    TextView movieText;
    @Bind(R.id.read_text)
    TextView readText;
    @Bind(R.id.music_text)
    TextView musicText;
    @Bind(R.id.line_tab)
    ImageView line_tab;  //tab选项卡的下划线
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    private ImageView searchImage;
    private int moveOne = 0;  //下划线移动一个选项卡
    private boolean isScrolling = false;  //手指是否在滑动
    private boolean isBackScorlling = false;  //手指离开后的回弹
    private long startTime = 0;
    private long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Toolbar
        toolbar.setTitle(R.string.app_lable);//设置主标题
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        initVIew();
        initLineImage();
    }
    //重新设定line的宽度
    private void initLineImage() {
        //获取屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        //重新设置下划线的宽度
        ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
        lp.width = screenW / 3;
        line_tab.setLayoutParams(lp);

        moveOne = lp.width;  //滑动一个页面的距离
    }

    private void initVIew() {
        searchImage = (ImageView) findViewById(R.id.search_toolbar);
        //ViewPager中包含的页面为Fragment，用法与前面的普通适配器一样
        MovieFragment movieFragment = new MovieFragment();
        ReadFragment readFragment = new ReadFragment();
        MusicFragment musicFragment = new MusicFragment();

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(movieFragment);
        fragmentList.add(readFragment);
        fragmentList.add(musicFragment);

        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(
                getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(myFragmentAdapter);

        mViewPager.setCurrentItem(0);
        movieText.setTextColor(Color.RED);
        readText.setTextColor(Color.BLACK);
        musicText.setTextColor(Color.BLACK);

        movieText.setOnClickListener(this);
        readText.setOnClickListener(this);
        musicText.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(this);
        mDrawerLayout.setOnClickListener(this);
        mNavigationView.setCheckedItem(R.id.nav_call);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1:
                isScrolling = true;
                isBackScorlling = false;
                break;
            case 2:
                isScrolling = false;
                isBackScorlling = true;
                break;
            case 3:
                isScrolling = false;
                isBackScorlling = false;
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime > 200)) {
            movePositionX(position, moveOne * positionOffset);
            startTime = currentTime;
        }
        if (isBackScorlling) {
            movePositionX(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                movieText.setTextColor(Color.RED);
                readText.setTextColor(Color.BLACK);
                musicText.setTextColor(Color.BLACK);
                movePositionX(0);
                break;
            case 1:
                movieText.setTextColor(Color.BLACK);
                readText.setTextColor(Color.RED);
                musicText.setTextColor(Color.BLACK);
                movePositionX(1);
                break;
            case 2:
                movieText.setTextColor(Color.BLACK);
                readText.setTextColor(Color.BLACK);
                musicText.setTextColor(Color.RED);
                movePositionX(2);
                break;
            default:
                break;
        }
    }
    //下划线跟随手指的滑动而移动
    private void movePositionX(int toPosition, float positionOffsetPixels) {
        float curTranslatioinX = line_tab.getTranslationX();
        float toPositionX = moveOne * toPosition + positionOffsetPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab,
                "translationX", curTranslatioinX, toPositionX);
        animator.setDuration(100);  //动画延迟--下划线延迟
        animator.start();
    }
    //下划线滑动到新的选项卡中
    private void movePositionX(int toPosition) {
        movePositionX(toPosition, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_text:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.read_text:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.music_text:
                mViewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_toolbar:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
