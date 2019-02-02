package com.example.hp.viewpagedemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by HP on 2017/2/7.
 */

public class MyPagerAdapter extends PagerAdapter {

    private List<View> pageList;

    public MyPagerAdapter(List<View> pageList) {
        this.pageList = pageList;
    }

    @Override  //初始化指定位置的页面，并且返回当前页面的本身
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pageList.get(position));  //获取指定位置的控件
        return pageList.get(position);
    }

    @Override  //返回要展示的页面数量
    public int getCount() {
        return pageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override  //移除指定位置的页面
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pageList.get(position));
    }
  /*  private ArrayList<View> listViews;
    private int size;
    public MyPagerAdapter(ArrayList<VIew> listViews) {
        this.listViews = listViews;
        size = listViews == null ? 0 : listViews.size();
    }

    public void setListViews(ArrayList<VIew> listViews) {
        this.listViews = listViews;
        size = listViews == null ? 0 : listViews.size();
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(listViews.get(position % size));
    }

    @Override
    public void finishUpdate(ViewGroup container) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            ((ViewPager) container).addView(listViews.get(position % size),0);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return listViews.get(position % size);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }*/


}
