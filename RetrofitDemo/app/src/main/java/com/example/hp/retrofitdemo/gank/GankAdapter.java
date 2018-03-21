package com.example.hp.retrofitdemo.gank;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.retrofitdemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by HP on 2017/4/10.
 */

public class GankAdapter extends RecyclerView.Adapter {

    private List<GankEntry> mGankEntries;

    public void setData(List<GankEntry> gankEntries) {
        mGankEntries = gankEntries;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gank_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        GankEntry entry = mGankEntries.get(position);
        ImageLoader.getInstance().displayImage(entry.url, viewHolder.mImageView);
        viewHolder.descText.setText(entry.desc);
        viewHolder.authorText.setText(entry.who);
    }

    @Override
    public int getItemCount() {
        return mGankEntries == null ? 0 :mGankEntries.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView descText;
        public TextView authorText;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.gank_image);
            descText = (TextView) itemView.findViewById(R.id.desc);
            authorText = (TextView) itemView.findViewById(R.id.author);
        }
    }
}
