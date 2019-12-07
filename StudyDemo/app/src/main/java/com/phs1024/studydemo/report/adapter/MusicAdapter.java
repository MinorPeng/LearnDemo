package com.phs1024.studydemo.report.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.base.OnItemBaseClickListener;
import com.phs1024.studydemo.report.bean.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PHS1024
 * @date 2019/10/24 18:13:01
 */
public class MusicAdapter extends RecyclerView.Adapter {

    private List<Music> mMusicList;
    private OnItemBaseClickListener mClickListener = null;

    public MusicAdapter() {
        mMusicList = new ArrayList<>();
    }

    public MusicAdapter(List<Music> musics) {
        this();
        this.mMusicList.addAll(musics);
    }

    public void setMusicList(List<Music> musicList) {
        this.mMusicList = musicList;
        notifyDataSetChanged();
    }

    public List<Music> getMusicList() {
        return mMusicList;
    }

    public Music getMusic(int position) {
        if (position >= 0 && position < mMusicList.size()) {
            return mMusicList.get(position);
        } else {
            return null;
        }
    }

    public void setClickListener(OnItemBaseClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Music music = mMusicList.get(position);
        if (holder instanceof MusicViewHolder && music != null) {
            ((MusicViewHolder) holder).mTvName.setText(music.getName());
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

    @Override
    public int getItemCount() {
        return mMusicList != null ? mMusicList.size() : 0;
    }

    class MusicViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;

        MusicViewHolder(@NonNull View itemView) {

            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_item_recycler_music);
        }
    }
}
