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
       /* TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimeRulerView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TimeRulerView_backgroundColor) {
                backgroundColor = a.getColor(attr, Color.GRAY);

            } else if (attr == R.styleable.TimeRulerView_linesColor) {// 默认颜色设置为黑色
                linesColor = a.getColor(attr, Color.BLACK);

            }

        }
        a.recycle();*/
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 200);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
