package com.example.a14512.customviewdemo.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 14512 on 2018/7/18
 */
public class SloopView extends View {
    private float mWidth;
    private float mHeight;
    private Paint mPaint = new Paint();

    private void initPaint() {
        //设置画笔颜色
        mPaint.setColor(Color.RED);
        //设置画笔模式为填充
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔宽度为10px
        mPaint.setStrokeWidth(5f);
    }

    /**
     * @param context
     * 一般在new一个view时调用
     */
    public SloopView(Context context) {
        this(context, null);
    }

    /**
     * 一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来。
     * @param context
     * @param attrs
     */
    public SloopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param defStyleAttr 一般默认style参数
     */
    public SloopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //取出宽度的确切数值
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        //取出高度的确切数值
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //取出宽度的测量模式
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        //取出高度的测量模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 视图大小发生改变时调用，再次确定view的大小
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 确定子View布局位置
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 绘制内容
     * @param canvas
     */
    @SuppressLint({"DrawAllocation", "NewApi"})
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制颜色是填充整个画布，常用于绘制底色。
//        canvas.drawColor(Color.BLUE);
//        canvas.drawPoint(200, 200, mPaint);
//        canvas.drawPoints(new float[] {500, 500, 500, 600, 500, 700}, mPaint);
//        // 在坐标(300,300)(500,600)之间绘制一条直线
//        canvas.drawLine(300, 300, 500, 600, mPaint);
//        // 绘制一组线 每四数字(两个点的坐标)确定一条线
//        canvas.drawLines(new float[]{
//                100,200,200,200,
//                100,300,200,300
//        },mPaint);
//
//        //绘制矩形，三种方法
//        canvas.drawRect(600, 600, 900, 650, mPaint);
//        //2
////        Rect rect = new Rect(600, 600, 900, 650);
////        canvas.drawRect(rect,mPaint);
//        //3
////        RectF rectF = new RectF(600, 600, 900, 650);
////        canvas.drawRect(rectF,mPaint);
//
//        //绘制圆角矩形、2种方法
//        // 第一种
//        RectF rectF = new RectF(100,100,800,400);
//        //dx、dy --- 30、30圆弧半径
//        canvas.drawRect(rectF, mPaint);
//        mPaint.setColor(Color.BLACK);
//        canvas.drawRoundRect(rectF,300,500,mPaint);
//        // 第二种
////        canvas.drawRoundRect(100,100,800,400,30,30,mPaint);
//
//        //绘制椭圆
//        mPaint.setColor(Color.GREEN);
//        // 第一种
//        canvas.drawOval(rectF,mPaint);
//        // 第二种
//        canvas.drawOval(100,100,800,400,mPaint);
//
//        //绘制圆
//        // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
//        canvas.drawCircle(500,500,400,mPaint);
//
//        //绘制圆弧
//        // 绘制背景矩形
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rectF,mPaint);
//
//        // 绘制圆弧
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(rectF,0,90,false,mPaint);
//
//        //-------------------------------------
//
//        RectF rectF2 = new RectF(100,600,800,900);
//        // 绘制背景矩形
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rectF2,mPaint);
//
//        // 绘制圆弧
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(rectF2,0,90,true,mPaint);

        // 在坐标原点绘制一个黑色圆形
//        mPaint.setColor(Color.BLACK);
//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,mPaint);
//        // 在坐标原点绘制一个蓝色圆形，在上一个的基础上
//        mPaint.setColor(Color.BLUE);
//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,mPaint);

        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

//        RectF rect = new RectF(-400,-400,400,400);   // 矩形区域
//
//        for (int i=0; i<=20; i++)
//        {
//            canvas.scale(0.9f,0.9f);
//            canvas.drawRect(rect,mPaint);
//        }

        canvas.drawCircle(0,0,400,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,mPaint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,mPaint);
            canvas.rotate(10);
        }
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(380, 0, 400, 0, mPaint);
        super.onDraw(canvas);
    }
}
