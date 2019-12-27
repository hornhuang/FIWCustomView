package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author fishinwater-1999
 * @version 2019-12-26
 */
public class FIWTextView extends View {

    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;// 默认黑色

    private Paint mPaint;

    /**
     * 这个函数会在代码中 new 是调用
     * TextView textView = new TextView(this);
     * @param context
     */
    public FIWTextView(Context context) {
        super(context, null);
    }

    /**
     * 在代码中 new 是调用
     * @param context
     * @param attrs
     */
    public FIWTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * 在布局中使用：自定义属性、样式
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public FIWTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        mText = array.getString(R.styleable.TextView_fiw_text);
        mTextColor = array.getColor(R.styleable.TextView_fiw_textColor, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_fiw_textSize, mTextSize);

        // 回收
        array.recycle();

        mPaint = new Paint();
        // 看锯齿 不模糊
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }


    /**
     * 测量布局的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 布局宽高都是有这个确定
        // 制定控件的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) { // 在布局中指定的 wrap_content
            // 测量
            Rect bounds = new Rect();
            // 获取文本 rect
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            width = bounds.width();

        } else if (widthMode == MeasureSpec.EXACTLY) { // 在布局中指定了确定的值 100dp match_parent fill_parent

        } else if (widthMode == MeasureSpec.UNSPECIFIED) { // 尽可能的大 （很少用到，一般在 ScrollView ListView 里）

        }



        if (heightMode == MeasureSpec.AT_MOST) { // 在布局中指定的 wrap_content
            // 测量
            Rect bounds = new Rect();
            // 获取文本 rect
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            height = bounds.height();

        } else if (heightMode == MeasureSpec.EXACTLY) { // 在布局中指定了确定的值 100dp match_parent fill_parent

        } else if (heightMode == MeasureSpec.UNSPECIFIED) { // 尽可能的大 （很少用到，一般在 ScrollView ListView 里）

        }

        // 设置控件宽高
        setMeasuredDimension(width, height);
    }

}
