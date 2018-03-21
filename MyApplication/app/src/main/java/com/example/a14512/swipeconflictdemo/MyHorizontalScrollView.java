package com.example.a14512.swipeconflictdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by 14512 on 2017/9/5.
 */

public class MyHorizontalScrollView extends HorizontalScrollView{
    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

    private LinearLayout mWapper;
    private ViewGroup mMenu;  //菜单区
    private ViewGroup mContent;  //内容区
    private int mScreenWidth;
    private int mMenuWidth;

    //分别记录上次滑动的坐标（onInterceptTouchEvent）
    private int lastXIntercept = 0;
    private int lastYIntercept = 0;



    private int mMenuRightPadding;

    private boolean once = false;
    private boolean isOpen;


    public MyHorizontalScrollView(Context context) {
        this(context, null);
    }

    /**
     * 未使用自定义View时调用
     *
     * @param  context
     * @param attrs
     * */
    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 使用自定义View时才调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * */
    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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

   public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
    }

    /**
     * 事件分发，冲突处理，外部拦截
     * */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //外部拦截无法实现逻辑判断
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchEvent(ev);  //处理内部拦截时
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //处理逻辑
//                if (mGiveUpTouchEventListener != null) {
//                    if (mGiveUpTouchEventListener.giveUpTouchEvent(ev)) {
//                        intercepted = true;
//                    }
//                } else {
//                    intercepted = false;
//                }
                //外部拦截ScrollView的上下滑动，当ScrollView上下滑动时，事件给ScrollView
                if (Math.abs(x - lastXIntercept) > Math.abs(y - lastYIntercept)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        lastXIntercept = x;
        lastYIntercept = y;
        return intercepted;

//        //此处为内部拦截时需要
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            onTouchEvent(ev);
//            return false;
//        }
//        return true;
    }

    /**
     * 滚动事调用
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth;  //1~0
        float leftAlpha = 0.6f + 0.4f * (1.0f - scale);  //透明度

        //属性动画TranslationX，默认动画有时间限制，需要自己去设置时间
        mMenu.animate().translationX(mMenuWidth * scale * 0.8f).alpha(leftAlpha).setDuration(0).start();
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

    //这里是为了使用外部拦截解决滑动冲突的接口
    public interface OnGiveUpTouchEventListener {
        public boolean giveUpTouchEvent(MotionEvent event);
    }

}
