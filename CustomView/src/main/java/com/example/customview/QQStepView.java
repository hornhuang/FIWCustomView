package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义 View 要点，先写思路，在写 Demo
 * @author fishinwater-1999
 * @version 2020-02-09
 */
public class QQStepView extends View {
    public final String TAG = getClass().getName();

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    /**
     * 这里 20 是指 px，而我们需要 dip
     */
    private int mBorderWidth = 20;
    private int mStepTextSize;
    private int mStepTextColor;
    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;
    private RectF rectF;
    /**
     * 总共的、当前的 步数
     */
    private int mStepMax;
    private int mCurrentStep;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 1  分析效果
        // 2  确定
        // 3  在布局中使用
        // 4  自定义 View 中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        // 设置边角光滑
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        // 画笔实心
        // 解决 边缘卡没了 描边有宽度
        mOutPaint.setStyle(Paint.Style.STROKE);


        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        // 设置边角光滑
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        // 画笔实心
        // 解决 边缘卡没了 描边有宽度
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mStepTextSize);
        mTextPaint.setColor(mStepTextColor);
        // 5  onMeasure
        // 6  画外圆弧、内圆弧、文字
        // 7  其他
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 调用者可能是 wrap_content 、match_content 、可能不一致
        // 这里就举判断一种不一致的情况为例
        // 由于计步器取正方形，所以取宽高的最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height:width, width > height ? height:width);
    }

    /**
     * 6 圆弧
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 6.1  画内圆弧
        int center = getWidth()/2;
        int radius = (getWidth() - mBorderWidth)/2;
        rectF = new RectF(center - radius,center - radius ,center + radius ,center + radius);
        // 问题 true 时会闭合 | 边缘卡没了 描边有宽度
        canvas.drawArc(/*填充色*/rectF, /*开始角度*/135, /*转过角度*/270, false, mOutPaint);
        // 6.2  画外圆弧
        if (mStepMax == 0) {return;}
        float sweepAngle = (float) mCurrentStep/mStepMax;
        canvas.drawArc(/*填充色*/rectF, /*开始角度*/135, /*转过角度*/270 * sweepAngle, false, mInnerPaint);
        // 6.3  画文字
        String stepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = getWidth()/2 - textBounds.width()/2;
        // 基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = fontMetricsInt.bottom - fontMetricsInt.top;
        int  baseLine = getHeight()/2 + dy/2;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);
    }

    /**
     * 7  其他  写几个方法动起来
     *    synchronized 防止多线程操作
     */
    public synchronized void setmStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    public void setmCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        // 不断绘制
        invalidate();
    }
}
