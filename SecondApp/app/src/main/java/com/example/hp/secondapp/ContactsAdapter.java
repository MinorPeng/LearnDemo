package com.example.hp.secondapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 2017/1/16.
 */

//适配器
public class ContactsAdapter extends ArrayAdapter<Contacts> {

    private int resuorceID;

    public ContactsAdapter(Context context, int textViewResuorceID,
                           List<Contacts> objects) {
        super(context, textViewResuorceID, objects);
        resuorceID = textViewResuorceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contacts contacts = getItem(position);  //获取当前项contacts的实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resuorceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contactsImage = (ImageView) view.findViewById(R.id.circle_img);
            viewHolder.contactsName = (TextView) view.findViewById(R.id.contacts_name);
            view.setTag(viewHolder);  //将ViewHolder存储在View中
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.contactsImage.setImageResource(contacts.getImageID());
        viewHolder.contactsName.setText(contacts.getName());
        return view;
    }
    //定义一个内部类，进行缓存
    class ViewHolder {

        ImageView contactsImage;

        TextView contactsName;
    }
}
