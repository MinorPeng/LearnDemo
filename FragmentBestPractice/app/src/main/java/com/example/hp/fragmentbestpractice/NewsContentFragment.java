package com.example.hp.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HP on 2017/1/20.
 */

public class NewsContentFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_content_frag, container, false);
        return mView;
    }

    public void refresh(String newsTitle, String newsContent) {
        View visibilityLayout =  mView.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView) mView.findViewById(R.id.news_title);
        TextView newsContentText = (TextView) mView.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);  //刷新新闻的标题
        newsContentText.setText(newsContent);  //刷新新闻的内容
    }
}
