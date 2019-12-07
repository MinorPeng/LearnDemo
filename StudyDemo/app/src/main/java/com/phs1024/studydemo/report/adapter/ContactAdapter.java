package com.phs1024.studydemo.report.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.base.OnItemBaseClickListener;
import com.phs1024.studydemo.report.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PHS1024
 * @date 2019/10/28 11:14:22
 */
public class ContactAdapter extends RecyclerView.Adapter {

    private List<Contact> mContacts;
    private OnItemBaseClickListener mClickListener = null;

    public ContactAdapter() {
        mContacts = new ArrayList<>();
    }

    public ContactAdapter(@NonNull List<Contact> contacts) {
        this();
        mContacts.addAll(contacts);
    }

    public void setContacts(@NonNull List<Contact> contacts) {
        mContacts = contacts;
        notifyDataSetChanged();
    }

    public List<Contact> getContacts() {
        return mContacts;
    }

    public void setClickListener(OnItemBaseClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_contact,
                parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContactViewHolder) {
            Contact contact = mContacts.get(position);
            if (contact != null) {
                ((ContactViewHolder) holder).mTvName.setText(contact.getName() + "\n" + contact.getPhone());
                if (mClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mClickListener.onClick(position, v);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mClickListener.onLongClick(position, v);
                            return true;
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPortrait;
        private TextView mTvName;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvPortrait = itemView.findViewById(R.id.iv_item_contact_portrait);
            mTvName = itemView.findViewById(R.id.tv_item_contact_name);
        }
    }
}
