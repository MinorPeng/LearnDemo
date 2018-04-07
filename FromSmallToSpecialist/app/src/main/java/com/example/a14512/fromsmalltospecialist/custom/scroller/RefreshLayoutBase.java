package com.example.a14512.fromsmalltospecialist.custom.scroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.a14512.fromsmalltospecialist.R;
import com.example.a14512.fromsmalltospecialist.custom.listener.OnLoadListener;
import com.example.a14512.fromsmalltospecialist.custom.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 14512 on 2018/4/2
 */

@SuppressLint("NewApi")
public abstract class RefreshLayoutBase<T extends View> extends ViewGroup implements AbsListView.OnScrollListener {

    /**
     * 滚动控制器
     */
    protected Scroller mScroller;

    /**
     * 下拉刷新时显示的header view
     */
    protected View mHeaderView;

    /**
     * 上拉加载显示的footer view
     */
    protected View mFooterView;

    /**
     * 本次触摸滑动y坐标上的偏移量
     */
    protected int mYOffset;

    /**
     * 内容视图，用户触摸导致下拉刷新、上拉加载的主视图，如ListView、GridView
     */
    protected T mContentView;

    /**
     * 最初的滚动位置，第一次布局时滚动header高度的距离
     */
    protected int mInitScrollY = 0;

    /**
     * 最后一次触摸事件的y轴坐标
     */
    protected int mLastY = 0;

    /**
     * 空闲状态
     */
    public static final int STATUS_IDLE = 0;

    /**
     * 下拉或上拉状态，还没达到可刷新的状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 1;

    /**
     * 下拉或者上拉状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 2;

    /**
     * 刷新中
     */
    public static final int STATUS_REFRESHING = 3;

    /**
     * Loading中
     */
    public static final int STATUS_LOADING = 4;

    /**
     * 当前状态
     */
    protected int mCurrentStatus = STATUS_IDLE;

    /**
     * header中的箭头图标
     */
    private ImageView mImgArrow;

    /**
     * 箭头是否向上
     */
    private boolean isArrowUp;

    /**
     * header中的文本标签
     */
    private TextView mTvTips;

    /**
     * header中时间标签
     */
    private TextView mTvTime;
    /**
     * header中的进度条
     */
    private ProgressBar mProgressBar;

    /**
     * 屏幕的高度
     */
    private int mScreenHeight;

    /**
     * header的高度
     */
    private int mHeaderHeight;

    /**
     * 下拉刷新回调
     */
    protected OnRefreshListener mOnRefreshListener;

    /**
     * 加载更多回调
     */
    protected OnLoadListener mOnLoadListener;


    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public RefreshLayoutBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

        mScroller = new Scroller(context);
        //获取屏幕高度
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        mHeaderHeight =  mScreenHeight / 4;
        initLayout(context);
    }

    /**
     * 初始化整个布局
     *
     * @param context
     */
    private final void initLayout(Context context) {
        //设置header view
        setupHeaderView(context);
        //设置内容视图
        setupContentView(context);
        //设置布局参数
        setDefaultContentLayoutParams();
        //添加内容视图
        addView(mContentView);
        setupFooterView(context);
    }

    /**
     * 初始化footer view
     * @param context
     */
    protected void setupFooterView(Context context) {
        mFooterView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer,
                this, false);
        addView(mFooterView);
    }

    /**
     * 设置content view的默认布局参数
     */
    protected void setDefaultContentLayoutParams() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    /**
     * 初始化content view，子类覆写
     * @param context
     */
    protected abstract void setupContentView(Context context);

    /**
     * 是否已经到了最顶部，子类需覆写该方法,使得mContentView滑动到最顶端时返回true,
     * 如果到达最顶端用户继续下拉则拦截事件;
     *
     * @return
     */
    protected abstract boolean isTop();

    /**
     * 子类需覆写该方法,使得mContentView滑动到最顶端时返回true, 如果到达最顶端用户继续下拉则拦截事件;
     *
     * @return
     */
    protected abstract boolean isBottom();

    /**
     * 初始化HeaderView
     * @param context
     */
    protected void setupHeaderView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header,
                this, false);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                mHeaderHeight));
        mHeaderView.setBackgroundColor(Color.RED);
        mHeaderView.setPadding(0, mHeaderHeight - 100, 0, 0);
        addView(mHeaderView);
    }

    /**
     * 丈量视图的宽高，宽度为用户设置的宽度，高度则为header、content view、footer这三个子控件的高度之和
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int finalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //measure
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //该view所需要的总高度
            finalHeight += child.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int top = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(0, top, child.getMeasuredWidth(), child.getMeasuredHeight() + top);
            top += child.getMeasuredHeight();
        }

        //计算初始化滑动的y轴距离
        mInitScrollY = mHeaderView.getMeasuredHeight() + getPaddingTop();
        //滑动到header view高度的位置，从而达到隐藏header view的效果
        scrollTo(0, mInitScrollY);
    }

    /**
     * 与Scroller合作,实现平滑滚动。在该方法中调用Scroller的computeScrollOffset来判断滚动是否结束。
     * 如果没有结束，那么滚动到相应的位置，并且调用postInvalidate方法重绘界面，
     * 从而再次进入到这个computeScroll流程，直到滚动结束。
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 在适当的时候拦截触摸事件，这里指的适当的时候是当mContentView滑动到顶部，
     * 并且是下拉时拦截触摸事件，否则不拦截，交给其child
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mYOffset = (int) (ev.getRawY() - mLastY);
                //如果拉到了顶部，并且是下拉，则拦截触摸时间，从而转到onTouchEvent来处理刷新事件
                if (isTop() && mYOffset > 0) {
                    return true;
                }
                break;
            default:
                break;
        }
        //默认不拦截，交给子view
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int currentY = (int) event.getRawY();
                //当前坐标减去按下去时的y坐标得到y轴上的偏移量
                mYOffset = currentY - mLastY;
                if (mCurrentStatus != STATUS_LOADING) {
                    //在y方向上滚动该控件
                    changeScrollY(mYOffset);
                }

                rotateHeaderArrow();
                changeTips();
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                //下拉刷新的操作
                doRefresh();
                break;
            default:
                break;
        }
        //当处于顶部并且向上滑动时，拦截事件并自己处理
        return true;
    }

    private void changeScrollY(int distance) {
        //最大值为scrollY header隐藏，最小值为0 header完全显示
        int curY = getScrollY();
        //下拉
        if (distance > 0 && curY - distance > getPaddingTop()) {
            scrollBy(0, -distance);
        } else if (distance < 0 && curY - distance <= mInitScrollY) {
            //上拉过程
            scrollBy(0, -distance);
        }

        curY = getScrollY();
        int slop = mInitScrollY / 2;
        if (curY > 0 && curY < slop) {
            mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
        } else if (curY > 0 && curY > slop) {
            mCurrentStatus = STATUS_PULL_TO_REFRESH;
        }
    }

    /**
     * 旋转箭头图标，header
     */
    protected void rotateHeaderArrow() {
        if (mCurrentStatus == STATUS_REFRESHING) {
            return;
        } else if (mCurrentStatus == STATUS_PULL_TO_REFRESH  && !isArrowUp) {
            return;
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH  && isArrowUp) {
            return;
        }

        mProgressBar.setVisibility(View.GONE);
        mImgArrow.setVisibility(View.VISIBLE);
        float pivotX = mImgArrow.getWidth() / 2f;
        float pivotY = mImgArrow.getHeight() / 2f;
        float fromDegress = 0f;
        float toDegress = 0f;
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegress = 180f;
            toDegress = 360f;
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegress = 0f;
            toDegress = 180f;
        }

        RotateAnimation animation = new RotateAnimation(fromDegress, toDegress, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        mImgArrow.startAnimation(animation);

        if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            isArrowUp = true;
        } else {
            isArrowUp = false;
        }
    }

    /**
     * 根据当前状态修改header view中的文本标签
     */
    protected void changeTips() {
        if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
            mTvTips.setText(R.string.pull_to_refresh_pull_label);
        } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
            mTvTips.setText(R.string.pull_to_refresh_release_label);
        }
    }

    /**
     * 设置滚动的参数
     * @param yOffset
     */
    private void startScroll(int yOffset) {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, yOffset);
        invalidate();
    }

    /**
     * 设置下拉刷新监听器
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    /**
     * 设置滑动到底部时自动加载更多的监听器
     * @param listener
     */
    public void setOnLoadingListener(OnLoadListener listener) {
        mOnLoadListener = listener;
    }

    /**
     * 刷新数据，恢复状态
     */
    public void refreshComplete() {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_IDLE;
        invalidate();
        updateHeaderTimeStamp();

        //200ms后处理arrow和Progressbar
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImgArrow.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }, 100);
    }

    /**
     * 加载结束，恢复状态
     */
    public void loadComplete() {
        //隐藏footer
        startScroll(mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_IDLE;
    }

    private void changeHeaderViewStatus() {
        int curScrollY = getScrollY();
        //超过1/2则认为是有效的下拉刷新，否则还原
        if (curScrollY < mInitScrollY / 2) {
            mScroller.startScroll(getScrollX(), curScrollY, 0,
                    mHeaderView.getPaddingTop() - curScrollY);
            mCurrentStatus = STATUS_REFRESHING;
            mTvTips.setText(R.string.pull_to_refresh_refreshing_label);
            mImgArrow.clearAnimation();
            mImgArrow.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mScroller.startScroll(getScrollX(), curScrollY, 0, mInitScrollY - curScrollY);
            mCurrentStatus = STATUS_IDLE;
        }

        invalidate();
    }

    /**
     * 执行下刷新
     */
    protected void doRefresh() {
        changeHeaderViewStatus();
        //执行刷新操作
        if (mCurrentStatus == STATUS_REFRESHING && mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }

    /**
     * 修改header上的最近更新时间
     */
    private void updateHeaderTimeStamp() {
        //设置更新时间
        mTvTime.setText(R.string.pull_to_refresh_update_time_label);
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        mTvTime.append(sdf.format(new Date()));
    }

    public T getContentView() {
        return mContentView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 滚动监听，滚动到最底部，且用户设置了加载更多的监听器是出发更多的操作
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnLoadListener != null && isBottom() && mScroller.getCurrY() <= mInitScrollY
                && mYOffset <= 0 && mCurrentStatus == STATUS_IDLE) {
            showFooterView();
            doLoadMore();
        }
    }

    /**
     * 执行下拉加载更多的操作
     */
    private void doLoadMore() {
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoadMore();
        }
    }

    /**
     * 显示footer view
     */
    private void showFooterView() {
        startScroll(mFooterView.getMeasuredHeight());
        mCurrentStatus = STATUS_LOADING;
    }
}
