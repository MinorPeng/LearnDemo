package com.example.a14512.recyclerviewandlistviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.recyclerviewandlistviewdemo.R;
import com.example.a14512.recyclerviewandlistviewdemo.data.Contact;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/4/29
 */
public class ListAdapter extends BaseAdapter {
    private ArrayList<Contact> mContacts;

    public ListAdapter(ArrayList<Contact> contacts) {
        this.mContacts = contacts;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_view, null);
            holder = new ListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }
        Contact contact = mContacts.get(position);
        holder.mPortrait.setImageResource(contact.getPortraitId());
        holder.mName.setText(contact.getName());
        return convertView;
    }

    public void swipeAddData(ArrayList<Contact> addData) {
        mContacts.addAll(0, addData);
        notifyDataSetChanged();
    }

    public void upAddData(ArrayList<Contact> addData) {
        mContacts.addAll(addData);
        notifyDataSetChanged();
    }

    public static class ListViewHolder {
        ImageView mPortrait;
        TextView mName;

        public ListViewHolder(View itemView) {
            mPortrait = itemView.findViewById(R.id.imgPortrait);
            mName = itemView.findViewById(R.id.tvName);
        }
    }
}
