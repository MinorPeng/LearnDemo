package com.example.a14512.audiovediodemo.wechat.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.a14512.audiovediodemo.wechat.widget.AutoTextureView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 14512 on 2018/9/6
 */
public class MediaUtils implements SurfaceHolder.Callback {
    private static final String TAG = "MediaUtils";

    public static final int MEDIA_AUDIO = 0;
    public static final int MEDIA_VIDEO = 1;
    private Activity mActivity;
    private MediaRecorder mMediaRecorder;
    private CamcorderProfile mProfile;
    private Camera mCamera;
    private AutoTextureView mAutoTextureView;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private File mTargetDir;
    private String mTargetName;
    private File mTargetFile;
    private int mPreviewWidth, mPreviewHeight;
    private int mRecorderType;
    private boolean mIsRecording;
    private GestureDetector mGestureDetector;
    private boolean mIsZoomIn = false;
    private int orientation = 90;
    /**
     * 0代表前置，1代表后置摄像头
     */
    private int mCameraPosition = 1;

    public MediaUtils(Activity activity) {
        mActivity = activity;
    }

    public void setRecorderType(int recorderType) {
        mRecorderType = recorderType;
    }

    public void setTargetDir(File targetDir) {
        mTargetDir = targetDir;
    }

    public void setTargetName(String targetName) {
        mTargetName = targetName;
    }

    public String getTargetFilePath() {
        return mTargetFile.getPath();
    }

    public boolean deleteTargetFile() {
        if (mTargetFile.exists()) {
            return mTargetFile.delete();
        } else {
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setSurfaceView(SurfaceView surfaceView) {
        mSurfaceView = surfaceView;
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFixedSize(mPreviewWidth, mPreviewHeight);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mGestureDetector = new GestureDetector(mActivity, new ZoomGestureListener());
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public int getPreviewWidth() {
        return mPreviewWidth;
    }

    public int getPreviewHeight() {
        return mPreviewHeight;
    }

    public boolean isRecording() {
        return mIsRecording;
    }

    public void record() {
        if (mIsRecording) {
            try {
                mMediaRecorder.stop();
            } catch (RuntimeException e) {
                mTargetFile.delete();
            }
            releaseMediaRecorder();
            mCamera.lock();
            mIsRecording = false;
        } else {
            startRecordThread();
        }
    }

    private void startRecordThread() {
        if (prepareRecord()) {
            try {
                mMediaRecorder.start();
                mIsRecording = true;
            } catch (RuntimeException r) {
                releaseMediaRecorder();
            }
        }
    }

    private boolean prepareRecord() {
        try {
            mMediaRecorder = new MediaRecorder();
            if (mRecorderType == MEDIA_VIDEO) {
                mCamera.unlock();
                mMediaRecorder.setCamera(mCamera);
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mMediaRecorder.setProfile(mProfile);
                if (mCameraPosition == 0) {
                    mMediaRecorder.setOrientationHint(270);
                } else {
                    mMediaRecorder.setOrientationHint(orientation);
                }
            } else if (mRecorderType == MEDIA_AUDIO) {
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            }
            mTargetFile = new File(mTargetDir, mTargetName);
            mMediaRecorder.setOutputFile(mTargetFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    public void stopRecordSave() {
        if (mIsRecording) {
            mIsRecording = false;
            try {
                mMediaRecorder.stop();
            } catch (RuntimeException r) {

            } finally {
                releaseMediaRecorder();
            }
        }
    }

    public void stopRecordUnSave() {
        if (mIsRecording) {
            mIsRecording = false;
            try {
                mMediaRecorder.stop();
            } catch (RuntimeException r) {
                if (mTargetFile.exists()) {
                    if (!mTargetFile.delete()) {
                        Log.d(TAG, "delete failed");
                    }
                }
            } finally {
                releaseMediaRecorder();
            }
            if (mTargetFile.exists()) {
                if (!mTargetFile.delete()) {
                    Log.d(TAG, "delete failed");
                }
            }
        }
    }

    private void startPreView(SurfaceHolder holder) {
        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(orientation);
            try {
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();
                List<Camera.Size> supportdPreviewSizes = parameters.getSupportedPreviewSizes();
                List<Camera.Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
                Camera.Size optimalSize = CameraHelpers.getOptimalVideoSize(supportedVideoSizes,
                        supportdPreviewSizes, mSurfaceView.getWidth(), mSurfaceView.getHeight());

                mPreviewWidth = optimalSize.width;
                mPreviewHeight = optimalSize.height;
                parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
                mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
                // 这里是重点，分辨率和比特率
                // 分辨率越大视频大小越大，比特率越大视频越清晰
                // 清晰度由比特率决定，视频尺寸和像素量由分辨率决定
                // 比特率越高越清晰（前提是分辨率保持不变），分辨率越大视频尺寸越大。
                mProfile.videoFrameWidth = optimalSize.width;
                mProfile.videoFrameHeight = optimalSize.height;
                // 这样设置 1080p的视频 大小在5M , 可根据自己需求调节
                mProfile.videoBitRate = 2 * optimalSize.width * optimalSize.height;
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        startPreView(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            releaseCamera();
        }
        if (mMediaRecorder != null) {
            releaseMediaRecorder();
        }
    }

    public void switchCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (mCameraPosition == 1) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    //代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头
                    startPreView(mSurfaceHolder);
                    mCameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    //代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头
                    startPreView(mSurfaceHolder);
                    mCameraPosition = 1;
                    break;
                }
            }
        }
    }

    private String getVideoThumb(String path) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(path);
        return bitmap2File(metadataRetriever.getFrameAtTime());
    }

    private String bitmap2File(Bitmap bitmap) {
        File thumbFile = new File(mTargetDir, mTargetName);
        if (thumbFile.exists()) {
            if (!thumbFile.delete()) {
                Log.d(TAG, "thumbFile delete failed");
            }
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(thumbFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbFile.getAbsolutePath();
    }


    private class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            if (!mIsZoomIn) {
                setZoom(20);
                mIsZoomIn = true;
            } else {
                setZoom(0);
                mIsZoomIn = false;
            }
            return true;
        }
    }

    private void setZoom(int zoomValue) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                mCamera.setParameters(parameters);
            }
        }
    }
}
