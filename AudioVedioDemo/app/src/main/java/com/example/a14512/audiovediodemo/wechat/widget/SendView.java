package com.example.a14512.audiovediodemo.wechat.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.a14512.audiovediodemo.R;
import com.example.a14512.audiovediodemo.wechat.util.Utils;

/**
 * @author 14512 on 2018/9/6
 */
public class SendView extends RelativeLayout {

    public RelativeLayout mBackLayout, mSelectLayout;

    public SendView(Context context) {
        this(context, null);
    }

    public SendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(
                Utils.getInstance(context).getWidthPixels(), Utils.getInstance(context).dp2px(180f));
        setLayoutParams(layoutParams);
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context)
                .inflate(R.layout.widget_view_send_btn, null, false);
        mBackLayout = layout.findViewById(R.id.return_layout);
        mSelectLayout = layout.findViewById(R.id.select_layout);
        addView(layout);
        setVisibility(GONE);
    }

    @SuppressLint("ObjectAnimatorBinding")
    public void startAnim() {
        setVisibility(VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mBackLayout, "translationX", 0, -360),
                ObjectAnimator.ofFloat(mSelectLayout, "translationX", 0, -360));
        set.setDuration(250).start();
    }

    public void stopAnim() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mBackLayout, "translationX", -360, 0),
                ObjectAnimator.ofFloat(mSelectLayout, "translationX", 360, 0));
        set.setDuration(250).start();
        setVisibility(GONE);
    }


}
