package com.phs1024.studydemo.report;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.icu.text.UnicodeSetSpanner;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.adapter.MusicAdapter;
import com.phs1024.studydemo.report.base.OnItemBaseClickListener;
import com.phs1024.studydemo.report.bean.Music;
import com.phs1024.studydemo.util.FileUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author PHS1024
 * @date 2019/10/21 10:33:17
 */
public class Report4MusicActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ImageButton mIbtnPlay;
    private AppCompatSeekBar mSeekBarMusic, mSeekBarAudio;
    private MusicAdapter mAdapter;
    private MusicThread mMusicThread;
    private MediaPlayer mMediaPlayer;
    private int mPlayPosition = -1;
    private volatile boolean mPlay = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report4_music);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view_report4_music);
        ImageButton ibtnPre = findViewById(R.id.ibtn_pre);
        ImageButton ibtnStop = findViewById(R.id.ibtn_stop);
        mIbtnPlay = findViewById(R.id.ibtn_play);
        ImageButton ibtnNext = findViewById(R.id.ibtn_next);
        mSeekBarMusic = findViewById(R.id.seek_bar_music);
        mSeekBarAudio = findViewById(R.id.seek_bar_audio);

        ibtnPre.setOnClickListener(this);
        ibtnStop.setOnClickListener(this);
        mIbtnPlay.setOnClickListener(this);
        ibtnNext.setOnClickListener(this);
        initMusicSeekBar();
        initAudioSeekBar();
        initRecyclerView();
    }

    @SuppressLint("InlinedApi")
    private void initData() {
        mMusicThread = new MusicThread();
        mMediaPlayer = new MediaPlayer();
        mMusicThread.start();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mIbtnPlay.setImageResource(android.R.drawable.ic_media_play);
                mp.stop();
                mPlay = false;
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(getLocalClassName(), "error:" + what + " extra:" + extra);
                //Toast.makeText(Report4MusicActivity.this, "error code:" + what, Toast.LENGTH_SHORT).show();
                mMediaPlayer.stop();
                return true;
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarMusic.setProgress(0);
                mSeekBarMusic.setMax(mp.getDuration());
                mIbtnPlay.setImageResource(android.R.drawable.ic_media_pause);
                mPlay = true;
                mp.start();
                //获取缓冲区的进度
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        mSeekBarMusic.setSecondaryProgress(mp.getDuration() / 100 * percent);
                    }
                });
            }
        });
        List<Music> musicList = FileUtil.getMusics();
        if (musicList == null) {
            Toast.makeText(this, "There is 0 music", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.setMusicList(FileUtil.getMusics());
        }
    }

    private void initMusicSeekBar() {
        mSeekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initAudioSeekBar() {
        final AudioManager audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        int audioMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curAudio = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSeekBarAudio.setProgress(curAudio);
        mSeekBarAudio.setMax(audioMax);
        mSeekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new MusicAdapter();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(new OnItemBaseClickListener() {
            @Override
            public void onClick(int position, View view) {
                playMusic(position);
            }

            @Override
            public void onLongClick(int position, View view) {

            }
        });
    }

    private void playMusic(int position) {
        Music music = mAdapter.getMusic(position);
        if (music == null) {
            return;
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(music.getPath());
            mMediaPlayer.prepareAsync();
            mPlay = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_pre:
                if (mPlayPosition == 0) {
                    mPlayPosition = mAdapter.getItemCount() - 1;
                } else {
                    mPlayPosition--;
                }
                playMusic(mPlayPosition);
                break;
            case R.id.ibtn_stop:
                mMediaPlayer.stop();
                break;
            case R.id.ibtn_play:
                if (mPlay) {
                    mMediaPlayer.pause();
                    mIbtnPlay.setImageResource(android.R.drawable.ic_media_play);
                    mPlay = false;
                } else {
                    mMediaPlayer.start();
                    mIbtnPlay.setImageResource(android.R.drawable.ic_media_pause);
                    mPlay = true;
                }
                break;
            case R.id.ibtn_next:
                if (mPlayPosition == mAdapter.getItemCount() - 1) {
                    mPlayPosition = 0;
                } else {
                    mPlayPosition++;
                }
                playMusic(mPlayPosition);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mMusicThread != null) {
            mMusicThread.interrupt();
            mMusicThread = null;
        }
    }

    class MusicThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (mSeekBarMusic.getProgress() <= mSeekBarMusic.getMax() && mMediaPlayer != null) {
                mSeekBarMusic.setProgress(mMediaPlayer.getCurrentPosition());
                SystemClock.sleep(500);
                //if (!mPlay) {
                //    break;
                //}
            }
        }
    }
}
