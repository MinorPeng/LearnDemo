package com.example.a14512.founddemo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a14512.founddemo.R;
import com.example.a14512.founddemo.fragment.FirstFregment;
import com.example.a14512.founddemo.fragment.SecondFragment;
import com.example.a14512.founddemo.fragment.ThirdFragment;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private TextView tvFirst;
    private TextView tvSecond;
    private TextView tvThird;
    private ImageView imgHead;

    private Fragment mFirstFrag, mSecondFrag, mThirdFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setDefaultFragment();  //设置默认Fragment
    }

    private void initView() {
        tvFirst = findViewById(R.id.tv_first);
        tvSecond = findViewById(R.id.tv_second);
        tvThird = findViewById(R.id.tv_third);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_new_channel);
        imgHead = navigationView.getHeaderView(0).findViewById(R.id.img_nav_header);
//        setDrawerLeftEdgeSize(this, mDrawerLayout, 1.0f);
        navigationView.setNavigationItemSelectedListener(
                (MenuItem item) -> {
                    int name = 0;
                    switch (item.getItemId()) {
                        case R.id.nav_new_group:
                            name = 1;
                            break;
                        case R.id.nav_new_channel:
                            name = 2;
                            break;
                        case R.id.nav_new_chat:
                            name = 3;
                            break;
                        case R.id.nav_contacts:
                            name = 4;
                            break;
                        case R.id.nav_calls:
                            name = 5;
                            break;
                        case R.id.nav_settings:
                            break;
                        case R.id.nav_about:
                            break;
                        case R.id.nav_login_out:
                            break;
                        default:
                            break;
                    }
                    Intent intent = new Intent(this, ItemActivity.class);
                    intent.putExtra("name", item.getTitle());
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                    return true;
                });

        tvFirst.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
        tvThird.setOnClickListener(this);
        imgHead.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 999) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.img_nav_header:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_first:
                tvFirst.setTextColor(Color.BLUE);
                tvSecond.setTextColor(Color.BLACK);
                tvThird.setTextColor(Color.BLACK);
                if (mFirstFrag == null) {
                    mFirstFrag = new FirstFregment();
                }
                transaction.replace(R.id.frame_layout, mFirstFrag);
                break;
            case R.id.tv_second:
                tvFirst.setTextColor(Color.BLACK);
                tvSecond.setTextColor(Color.BLUE);
                tvThird.setTextColor(Color.BLACK);
                if (mSecondFrag == null) {
                    mSecondFrag = new SecondFragment();
                }
                transaction.replace(R.id.frame_layout, mSecondFrag);
                break;
            case R.id.tv_third:
                tvFirst.setTextColor(Color.BLACK);
                tvSecond.setTextColor(Color.BLACK);
                tvThird.setTextColor(Color.BLUE);
                if (mThirdFrag == null) {
                    mThirdFrag = new ThirdFragment();
                }
                transaction.replace(R.id.frame_layout, mThirdFrag);
                break;
            default:
                break;
        }
        transaction.commit();
    }

   /* //通过反射设置滑动距离，滑动速度过快时，会出现滑动到右边的情况，左边出现一下空白
    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout,
                                             float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) {
            return;
        }
        try {
            //当传入键值为mRightDragger时，实现右拉全局滑动，获得右边的距离，为mLeftDragger时，为左边
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int)
                    (dm.widthPixels * displayWidthPercentage)));
        } catch (Exception e) {
        }
    }*/


    private void setDefaultFragment() {
        tvFirst.setTextColor(Color.BLUE);
        tvSecond.setTextColor(Color.BLACK);
        tvThird.setTextColor(Color.BLACK);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFirstFrag = new FirstFregment();
        transaction.replace(R.id.frame_layout, mFirstFrag);
        transaction.commit();

    }

}
