package com.phs1024.studydemo.report;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.ImageReader;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.adapter.ContactAdapter;
import com.phs1024.studydemo.report.base.OnItemBaseClickListener;
import com.phs1024.studydemo.report.bean.Contact;
import com.phs1024.studydemo.util.ContentProviderUtil;
import com.phs1024.studydemo.util.TimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author PHS1024
 * @date 2019/10/25 23:39:56
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Report5Activity extends AppCompatActivity {
    private ContactAdapter mAdapter;

    private static final int SETIMAGE = 1;
    private static final int MOVE_FOCK = 2;
    /**
     * Max preview width that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_WIDTH = 1920;

    /**
     * Max preview height that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private TextureView mTextureView;
    private ImageView mThumbnail;
    private Button mButton;
    private Handler mHandler;
    private Handler mUIHandler;
    private ImageReader mImageReader;
    private CaptureRequest.Builder mPreViewBuidler;
    private CameraCaptureSession mCameraSession;
    private CameraCharacteristics mCameraCharacteristics;
    private Surface mSurface;
    private Size mPreViewSize;
    private Rect mMaxZoomRect;
    private int mMaxRealRadio;
    /**
     * 相机缩放相关Rect
     */
    private Rect picRect;

    /**
     * 相机的Texture的触摸监听，用于放大缩小 比例可以自己控制
     */
    private View.OnTouchListener mTextureOnTouchListener = new View.OnTouchListener() {
        //时时当前的zoom
        double mZoom;
        //0<缩放比<mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM).intValue();
        //上次缩放前的zoom
        double mLastZoom;
        //两个手刚一起碰到手机屏幕的距离
        double mLenth;
        int mCount;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.e("local", "touch");
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mCount = 1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mCount >= 2) {
                        float x1 = event.getX(0);
                        float y1 = event.getY(0);
                        float x2 = event.getX(1);
                        float y2 = event.getY(1);
                        float x = x1 - x2;
                        float y = y1 - y2;
                        Double lenthRec = Math.sqrt(x * x + y * y) - mLenth;
                        Double viewLenth = Math.sqrt(v.getWidth() * v.getWidth() + v.getHeight()
                                * v.getHeight());
                        mZoom = ((lenthRec / viewLenth) * mMaxRealRadio) + mLastZoom;
                        picRect.top = (int) (mMaxZoomRect.top / (mZoom));
                        picRect.left = (int) (mMaxZoomRect.left / (mZoom));
                        picRect.right = (int) (mMaxZoomRect.right / (mZoom));
                        picRect.bottom = (int) (mMaxZoomRect.bottom / (mZoom));
                        Message.obtain(mUIHandler, MOVE_FOCK).sendToTarget();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mCount = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mCount++;
                    if (mCount == 2) {
                        float x1 = event.getX(0);
                        float y1 = event.getY(0);
                        float x2 = event.getX(1);
                        float y2 = event.getY(1);
                        float x = x1 - x2;
                        float y = y1 - y2;
                        mLenth = Math.sqrt(x * x + y * y);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mCount--;
                    if (mCount < 2) {
                        mLastZoom = mZoom;
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    /**
     * 拍照按钮的touch监听，down拍照，up更新相机预览
     */
    private View.OnTouchListener mBtnOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        //拍照
                        mCameraSession.setRepeatingRequest(initDngBuilder().build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        Toast.makeText(Report5Activity.this, "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    try {
                        updateCameraPreviewSession();
                    } catch (CameraAccessException e) {
                        Toast.makeText(Report5Activity.this, "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏无状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_report5);
        initCameraView();
        //initView();
        //getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSession != null) {
            mCameraSession.getDevice().close();
            mCameraSession.close();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initCameraView() {
        mTextureView = findViewById(R.id.texture_view);
        mButton = findViewById(R.id.btn_take_pic);
        mThumbnail = findViewById(R.id.iv_thumbnail);
        mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Report5Activity.this, "别戳了，那个页面还没写", Toast.LENGTH_SHORT).show();
            }
        });

        mUIHandler = new Handler(new InnerCallBack());
        //初始化相机布局
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                setupCamera(width, height);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        mTextureView.setOnTouchListener(mTextureOnTouchListener);
    }

    /**
     * 对相机进行初始化设置
     * @param with
     * @param height
     */
    @SuppressLint({"MissingPermission", "ClickableViewAccessibility"})
    private void setupCamera(int with, int height) {
        //相机线程
        HandlerThread thread = new HandlerThread("CameraThread");
        thread.start();
        mHandler = new Handler(thread.getLooper());
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = CameraCharacteristics.LENS_FACING_FRONT + "";
        try {
            mCameraCharacteristics = manager.getCameraCharacteristics(cameraId);
            //画面传感器的面积，单位是像素。
            mMaxZoomRect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            //最大的数字缩放
            mMaxRealRadio = Objects.requireNonNull(mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).intValue();
            picRect = new Rect(mMaxZoomRect);
            StreamConfigurationMap map = mCameraCharacteristics.get(CameraCharacteristics
                    .SCALER_STREAM_CONFIGURATION_MAP);
            Size largest = Collections.max(Arrays.asList(Objects.requireNonNull(map).getOutputSizes(ImageFormat.JPEG)), new CompareSizeByArea());
            mPreViewSize = map.getOutputSizes(SurfaceTexture.class)[0];
            choosePreSize(with, height, map, largest);
            mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                    ImageFormat.JPEG, 5);
            mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {

                    mHandler.post(new ImageSaver(reader.acquireNextImage()));
                }
            }, mHandler);
            //打开相机
            manager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    Log.d(getLocalClassName(), "相机已经打开");
                    preCamera(camera);
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {

                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            }, mHandler);
            //设置点击拍照的监听
            mButton.setOnTouchListener(mBtnOnTouchListener);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算预览的范围大小
     * @param width
     * @param height
     * @param map
     * @param largest
     */
    private void choosePreSize(int width, int height, StreamConfigurationMap map, Size largest) {
        int displayRotation = getWindowManager().getDefaultDisplay().getRotation();
        //noinspection ConstantConditions
        Integer sensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        boolean swappedDimensions = false;
        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    swappedDimensions = true;
                }
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    swappedDimensions = true;
                }
                break;
            default:
                Log.e(getLocalClassName(), "Display rotation is invalid: " + displayRotation);
        }
        android.graphics.Point displaySize = new android.graphics.Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        int rotatedPreviewWidth = width;
        int rotatedPreviewHeight = height;
        int maxPreviewWidth = displaySize.x;
        int maxPreviewHeight = displaySize.y;

        if (swappedDimensions) {
            rotatedPreviewWidth = height;
            rotatedPreviewHeight = width;
            maxPreviewWidth = displaySize.y;
            maxPreviewHeight = displaySize.x;
        }

        if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
            maxPreviewWidth = MAX_PREVIEW_WIDTH;
        }

        if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
            maxPreviewHeight = MAX_PREVIEW_HEIGHT;
        }
        mPreViewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                maxPreviewHeight, largest);
    }

    /**
     * 选择最合适的大小 参考网上代码
     * @param choices
     * @param textureViewWidth
     * @param textureViewHeight
     * @param maxWidth
     * @param maxHeight
     * @param aspectRatio
     * @return
     */
    private Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {
        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * 3 / 4) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }
        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizeByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizeByArea());
        } else {
            Log.e(getLocalClassName(), "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    /**
     * 设置预览
     * @param camera
     */
    private void preCamera(CameraDevice camera) {
        try {
            mPreViewBuidler = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            texture.setDefaultBufferSize(mPreViewSize.getWidth(), mPreViewSize.getHeight());
            mSurface = new Surface(texture);
            mPreViewBuidler.addTarget(mSurface);
            //相机会话的监听器，通过他得到mCameraSession对象，这个对象可以用来发送预览和拍照请求
            camera.createCaptureSession(Arrays.asList(mSurface, mImageReader.getSurface
                    ()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        mCameraSession = session;
                        session.setRepeatingRequest(mPreViewBuidler.build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updateCameraPreviewSession() throws CameraAccessException {
        mPreViewBuidler.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
        mPreViewBuidler.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        mCameraSession.setRepeatingRequest(mPreViewBuidler.build(), null, mHandler);
    }

    /**
     * 设置连拍的参数 每次拍照的一些相机属性设置
     * @return
     */
    private CaptureRequest.Builder initDngBuilder() {
        CaptureRequest.Builder captureBuilder = null;
        try {
            captureBuilder = mCameraSession.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureBuilder.addTarget(mImageReader.getSurface());
            captureBuilder.addTarget(mSurface);
            // Required for RAW capture
            captureBuilder.set(CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE, CaptureRequest.STATISTICS_LENS_SHADING_MAP_MODE_ON);
            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, (long) ((214735991 - 13231) / 2));
            captureBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 0);
            //设置 ISO，感光度
            captureBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, (10000 - 100) / 2);
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90);
            //设置每秒30帧
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraid = CameraCharacteristics.LENS_FACING_FRONT + "";
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraid);
            Range<Integer>[] fps = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            if (fps != null) {
                captureBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fps[fps.length - 1]);
            }
        } catch (CameraAccessException e) {
            Toast.makeText(this, "请求相机权限被拒绝", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "打开相机失败", Toast.LENGTH_SHORT).show();
        }
        return captureBuilder;
    }

    /**
     * 用于拍照后的显示
     */
    private class InnerCallBack implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SETIMAGE:
                    Bitmap bm = (Bitmap) message.obj;
                    mThumbnail.setImageBitmap(bm);
                    break;
                case MOVE_FOCK:
                    mPreViewBuidler.set(CaptureRequest.SCALER_CROP_REGION, picRect);
                    try {
                        mCameraSession.setRepeatingRequest(mPreViewBuidler.build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    public class CompareSizeByArea implements java.util.Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_report5_contact);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        mAdapter = new ContactAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(new OnItemBaseClickListener() {
            @Override
            public void onClick(int position, View view) {
                Contact contact = mAdapter.getContacts().get(position);
                if (contact != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + contact.getPhone());
                    intent.setData(data);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(int position, View view) {

            }
        });
    }

    private void getData() {
        List<Contact> contacts = ContentProviderUtil.getContacts(this);
        if (contacts == null) {
            Toast.makeText(this, "contact is 0 or some error", Toast.LENGTH_SHORT).show();
            Log.e(getLocalClassName(), "contact is null");
        } else {
            mAdapter.setContacts(contacts);
        }
    }

    /**
     * 照片保存线程
     */
    class ImageSaver implements Runnable {
        private Image mImage;

        ImageSaver(Image image) {
            mImage = image;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            //只能取一次值，第二次取就会为null
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            String path = Environment.getExternalStorageDirectory() + "/DCIM/Report/";
            File mImageFile = new File(path);
            if (!mImageFile.exists()) {
                boolean mkdir = mImageFile.mkdir();
                if (!mkdir) {
                    Log.e(this.getClass().getName(), "mkdir error");
                    return;
                }
            }
            String timeStamp = TimeUtil.getFormatTime("yyyyMMdd_HHmmss");
            String fileName = path + "IMG_" + timeStamp + ".jpg";
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileName);
                byte[] buff = new byte[buffer.remaining()];
                buffer.get(buff);
                BitmapFactory.Options ontain = new BitmapFactory.Options();
                ontain.inSampleSize = 50;
                Bitmap bm = BitmapFactory.decodeByteArray(buff, 0, buff.length, ontain);
                Message.obtain(mUIHandler, SETIMAGE, bm).sendToTarget();
                fos.write(buff);
                Log.d(getLocalClassName(), "保存图片完成");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
