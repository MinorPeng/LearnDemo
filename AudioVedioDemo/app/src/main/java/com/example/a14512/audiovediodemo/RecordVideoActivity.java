package com.example.a14512.audiovediodemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

/**
 * @author 14512 on 2018/9/4
 */
public class RecordVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mImgBtnRecord, mImgBtnStop;
    private File mVideoFile;
    private MediaRecorder mMediaRecorder;
    private SurfaceView mSurfaceView;
    private boolean isRecording = false;
    private int mScreenWith, mScreenHeight;
    private String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        Log.d("record", String.valueOf(getRequestedOrientation()));
        //横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //选择支持半透明模式，在有surfaceview得Activity使用
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        getScreenWithAndHeight();
        initView();

    }

    private void initView() {
        mImgBtnRecord = findViewById(R.id.img_btn_record);
        mImgBtnStop = findViewById(R.id.img_btn_stop);
        mImgBtnStop.setEnabled(false);
        mImgBtnRecord.setOnClickListener(this);
        mImgBtnStop.setOnClickListener(this);

        mSurfaceView = findViewById(R.id.surface_view);
        //设置分辨率
        mSurfaceView.getHolder().setFixedSize(mScreenWith, mScreenHeight);
        //设置该组件让屏幕不会自动关闭
        mSurfaceView.getHolder().setKeepScreenOn(true);

        Button btnPlayer = findViewById(R.id.btn_to_player);
        btnPlayer.setOnClickListener(this);
    }

    private void getScreenWithAndHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWith = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_record:
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(this, "no SD", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    mVideoFile = new File(getFilePath());
                    mMediaRecorder = new MediaRecorder();
                    mMediaRecorder.reset();
                    //设置从麦克风采集的声音
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置从摄像头采集图像
                    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    //设置视频文件的输出格式
                    //必须在设置声音编码格式、图像编码格式之前设置
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //设置声音编码格式
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //设置图像编码格式
                    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                    // 设置视频质量
//                    CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
                    mMediaRecorder.setVideoSize(mScreenWith, mScreenHeight);
//                    mMediaRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);
                    // 设置高质量录制， 改变码率
                    mMediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
                    //每秒1帧
                    mMediaRecorder.setVideoFrameRate(60);
                    mMediaRecorder.setOutputFile(mVideoFile.getAbsolutePath());
                    //指定使用surfaceview
                    mMediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
                    mMediaRecorder.prepare();
                    //开始录制
                    mMediaRecorder.start();
                    Log.d("recordvideo", "start");
                    mImgBtnRecord.setEnabled(false);
                    mImgBtnStop.setEnabled(true);
                    isRecording = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_btn_stop:
                if (isRecording) {
                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                    filePath = mVideoFile.getAbsolutePath();
                    mImgBtnRecord.setEnabled(true);
                    mImgBtnStop.setEnabled(false);

                }
                break;
            case R.id.btn_to_player:
                startActivity(new Intent(RecordVideoActivity.this, PlayerActivity.class)
                        .putExtra("file_path", filePath));
            default:
                break;
        }
    }

    private String getFilePath() throws IOException {
        String time = TimeUtil.getCurrentTime();
        File file = new File(Environment.getExternalStorageDirectory().getCanonicalFile()
                + "/AduioVedioDemo/");
        if (!file.exists()) {
            if (!file.mkdir()) {
                Toast.makeText(this, "mkdir fialed", Toast.LENGTH_SHORT).show();
            }
        }

        return file.getAbsolutePath() + "/" + time + ".3gp";
    }
}
