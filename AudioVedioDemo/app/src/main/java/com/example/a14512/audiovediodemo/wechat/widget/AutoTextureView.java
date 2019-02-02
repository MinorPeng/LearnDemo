package com.example.a14512.audiovediodemo.wechat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * @author 14512 on 2018/9/6
 */
public class AutoTextureView extends TextureView {

    private int mRatioWith = 0;
    private int mRatioHeight = 0;

    public AutoTextureView(Context context) {
        this(context, null);
    }

    public AutoTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAspectRatio(int with, int height) {
        if (with < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        mRatioWith = with;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mRatioWith == 0 || mRatioHeight == 0) {
            setMeasuredDimension(with, height);
        } else {
            if (with < height * mRatioWith / mRatioHeight) {
                setMeasuredDimension(with, with * mRatioHeight / mRatioWith);
            } else {
                setMeasuredDimension(height * mRatioWith / mRatioHeight, height);
            }
        }
    }
}
