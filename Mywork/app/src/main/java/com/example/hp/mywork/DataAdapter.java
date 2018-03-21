package com.example.hp.mywork;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 2017/1/21.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{

    private List<Data> mDataList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView mImageView;
        TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mImageView = (ImageView) view.findViewById();
            mTextView = (TextView) view.findViewById(R.id.message_item);
        }
    }

    public DataAdapter(List<Data> dataList) {
        mDataList = dataList;
    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        final Data data = mDataList.get(position);
        //holder.mImageView.setImageResource(data.getImageID());
        holder.mTextView.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
