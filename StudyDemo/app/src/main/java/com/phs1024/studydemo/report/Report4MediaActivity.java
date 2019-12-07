package com.phs1024.studydemo.report;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.util.FileUtil;

import java.io.IOException;

/**
 * @author PHS1024
 * @date 2019/10/21 10:33:49
 */
public class Report4MediaActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView mSurfaceView;
    private AppCompatSeekBar mSeekBarVideo;
    private ImageButton mIbtnPlay;
    private MediaPlayer mMediaPlayer;
    private VideoThread mVideoThread;
    private boolean mPlay = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report4_media);
        initView();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.surface_view);
        mSeekBarVideo = findViewById(R.id.seek_bar_media);
        ImageButton ibtnStop = findViewById(R.id.ibtn_stop);
        mIbtnPlay = findViewById(R.id.ibtn_play);

        ibtnStop.setOnClickListener(this);
        mIbtnPlay.setOnClickListener(this);

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mMediaPlayer == null) {
                    initMedia();
                }
                if (mMediaPlayer != null) {
                    mMediaPlayer.setDisplay(holder);
                    mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
                }
                playStart();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mSeekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mMediaPlayer != null) {
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

    @SuppressLint("NewApi")
    private void initMedia() {
        mMediaPlayer = new MediaPlayer();
        mVideoThread = new VideoThread();
        mVideoThread.start();
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(getLocalClassName(), "error:" + what + " extra:" + extra);
                //Toast.makeText(Report4MediaActivity.this, "error:" + what, Toast.LENGTH_SHORT).show();
                mMediaPlayer.stop();
                mPlay = false;
                return true;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.stop();
                mIbtnPlay.setImageResource(android.R.drawable.ic_media_play);
                mPlay = false;
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarVideo.setProgress(0);
                mSeekBarVideo.setMax(mp.getDuration());
                mPlay = true;
                mIbtnPlay.setImageResource(android.R.drawable.ic_media_pause);
                mMediaPlayer.start();
            }
        });
    }

    private void playStart() {
        Uri uri = FileUtil.getVideo();
        if (uri != null) {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(this, uri);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "uri is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoThread != null) {
            mVideoThread.interrupt();
            mVideoThread = null;
        }
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    class VideoThread extends Thread {
        @Override
        public void run() {
            while (mSeekBarVideo.getProgress() <= mSeekBarVideo.getMax() && mMediaPlayer != null) {
                mSeekBarVideo.setProgress(mMediaPlayer.getCurrentPosition());
                SystemClock.sleep(500);
                //if (mStop) {
                //    break;
                //}
            }
        }
    }
}
