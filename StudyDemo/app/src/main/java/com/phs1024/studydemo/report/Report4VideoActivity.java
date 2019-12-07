package com.phs1024.studydemo.report;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.util.FileUtil;

/**
 * @author PHS1024
 * @date 2019/10/21 10:33:33
 */
public class Report4VideoActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView mVideoView;
    private ImageButton mIbtnPlay;
    private boolean mPlay = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report4_video);
        initView();
        initVideoView();
    }

    private void initView() {
        mVideoView = findViewById(R.id.video_view);
        ImageButton ibtnStop = findViewById(R.id.ibtn_stop);
        mIbtnPlay = findViewById(R.id.ibtn_play);

        ibtnStop.setOnClickListener(this);
        mIbtnPlay.setOnClickListener(this);
    }

    private void initVideoView() {
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(getLocalClassName(), "error:" + what + " extra:" + extra);
                //Toast.makeText(Report4VideoActivity.this, "error:" + what, Toast.LENGTH_SHORT).show();
                mVideoView.stopPlayback();
                mPlay = false;
                return true;
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlay = true;
                mIbtnPlay.setImageResource(android.R.drawable.ic_media_pause);
                mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.stopPlayback();
                mPlay = false;
                mIbtnPlay.setImageResource(android.R.drawable.ic_media_play);
            }
        });
        playStart();
    }

    private void playStart() {
        Uri uri = FileUtil.getVideo();
        if (uri == null) {
            Toast.makeText(this, "There is 0 video", Toast.LENGTH_SHORT).show();
        } else {
            mVideoView.setVideoURI(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_stop:
                mVideoView.stopPlayback();
                break;
            case R.id.ibtn_play:
                if (mPlay) {
                    mVideoView.pause();
                    mIbtnPlay.setImageResource(android.R.drawable.ic_media_play);
                    mPlay = false;
                } else {
                    mVideoView.start();
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
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mVideoView.stopPlayback();
            }
            mVideoView = null;
        }
    }
}
