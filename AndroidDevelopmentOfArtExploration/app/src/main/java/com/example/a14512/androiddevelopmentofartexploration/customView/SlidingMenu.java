package com.example.a14512.androiddevelopmentofartexploration.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.a14512.androiddevelopmentofartexploration.R;

/**
 * Created by 14512 on 2017/9/4.
 */

public class SlidingMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;  //菜单区
    private ViewGroup mContent;  //内容区
    private int mScreenWidth;
    private int mMenuWidth;


    private int mMenuRightPadding;

    private boolean once = false;
    private boolean isOpen;


    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 未使用自定义View时调用
     *
     * @param  context
     * @param attrs
     * */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 使用自定义View时才调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        int initSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
        mMenuRightPadding = a.getDimensionPixelSize(R.styleable.SlidingMenu_rightPadding, initSize);
        a.recycle();

         /*   WindowManager windowManager = (WindowManager) context.getSystemServiceName(Class.forName(Context.WINDOW_SERVICE));
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);*/

        mScreenWidth = displayMetrics.widthPixels;

    }

    /**
     * 设置自己的宽和高
     * 设置子view的宽和高
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量来将menu隐藏
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX= getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);  //隐藏时有动画
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
            return true;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 滚动事调用
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth;  //1~0
        /**
         * 内容区域1.0~0.7缩放的效果
         * scale：1.0~0.0
         * 0.7 + 0.3 * scale
         *
         * 菜单显示时的缩放以及透明度变化
         * 缩放：0.7~1.0
         * 1.0 - scale * 0.3
         * 透明度：0.6~1.0
         * 0.6 + 0.4 * (1 - scale)
         * */
        float rightScale = 0.7f + 0.3f * scale;  //缩放值
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1.0f - scale);  //透明度

        //属性动画TranslationX，默认动画有时间限制，需要自己去设置时间
        mMenu.animate().translationX(mMenuWidth * scale * 0.8f).alpha(leftAlpha).setDuration(0).start();
//        mMenu.animate().scaleX(leftScale);  //横向缩放
//        mMenu.animate().scaleY(leftScale);
//        mMenu.animate().alpha(leftAlpha);
        //设置内容区缩放
//        mContent.setPivotX(0);
//        mContent.setPivotY(mContent.getHeight() / 2);
//        mContent.animate().scaleX(rightScale).scaleY(rightScale);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     * */
    public void closeMenu() {
        if (!isOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    /**
     * 切换菜单
     * */
    public void changeMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
