package com.example.hp.viewpagedemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by HP on 2017/2/7.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    public MyFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
