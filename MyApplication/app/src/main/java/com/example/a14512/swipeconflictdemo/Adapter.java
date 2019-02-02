package com.example.a14512.swipeconflictdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 14512 on 2017/9/20.
 */

public class Adapter extends RecyclerView.Adapter {
    private List<String> strings;
    private Context context;

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_recycler, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            ((Holder) holder).textView.setText(strings.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.size();
    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
