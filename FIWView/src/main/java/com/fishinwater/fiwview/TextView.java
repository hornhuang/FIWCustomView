package com.fishinwater.fiwview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author fishinwater-1999
 * @version 2019-12-26
 */
public class TextView extends View {
    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;// 默认黑色

    private Paint mPaint;

    /**
     * 这个函数会在代码中 new 是调用
     * TextView textView = new TextView(this);
     * @param context
     */
    public TextView(Context context) {
        super(context, null);
    }

    /**
     * 在代码中 new 是调用
     * @param context
     * @param attrs
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * 在布局中使用：自定义属性、样式
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        mText = array.getString(R.styleable.TextView_text);

        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
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

        if (widthMode == MeasureSpec.AT_MOST) { // 在布局中指定的 wrap_content

        } else if (widthMode == MeasureSpec.EXACTLY) { // 在布局中指定了确定的值 100dp match_parent fill_parent

        } else if (widthMode == MeasureSpec.UNSPECIFIED) { // 尽可能的大 （很少用到，一般在 ScrollView ListView 里）

        }
    }

}
