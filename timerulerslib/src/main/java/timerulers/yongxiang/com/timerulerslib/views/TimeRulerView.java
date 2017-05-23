package timerulers.yongxiang.com.timerulerslib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import timerulers.yongxiang.com.timerulerslib.R;

/**
 * Created by dingyongxiang on 2017/5/22.
 */

public class TimeRulerView extends View {

    private float viewHeight;
    private float viewWeight;
    private int backgroundColor;
    private int linesColor;


    public TimeRulerView(Context context) {
        super(context);
    }

    public TimeRulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeRulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimeRulerView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TimeRulerView_backgroundColor) {
                backgroundColor = a.getColor(attr, Color.GRAY);

            } else if (attr == R.styleable.TimeRulerView_linesColor) {// 默认颜色设置为黑色
                linesColor = a.getColor(attr, Color.BLACK);

            }

        }
        a.recycle();
    }

    public TimeRulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            float textWidth = mBounds.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
            float textHeight = mBounds.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }


        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
