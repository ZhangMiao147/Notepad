package com.zhangmiao.notepad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zhangmiao.notepad.R;

/**
 * 画一个小表盘
 * Author: zhangmiao
 * Date: 2017/10/9
 */
public class TimeView extends View {

    private static final String TAG = TimeView.class.getSimpleName();

    private Paint mPaint;
    private int mNum;//时间（小时）
    private int mClockColor; //表盘颜色
    private int mClockwiseColor; //时针颜色

    public TimeView(Context context) {
        super(context);
        init();
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttris(context, attrs);
    }

    private void init() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
    }

    private void getAttris(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        mNum = ta.getInt(R.styleable.TimeView_num, 0);
        mClockColor = ta.getColor(R.styleable.TimeView_clock_color, Color.WHITE);
        mClockwiseColor = ta.getColor(R.styleable.TimeView_clockwise_color, Color.BLACK);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPaint == null) {
            mPaint = new Paint();
        }
        int r = getWidth() / 2;
        int centerX = r;
        int centerY = r;

        int spacing = r / 5;

        mPaint.setColor(mClockColor);
        canvas.drawCircle(centerX, centerY, r, mPaint);

        mPaint.setColor(mClockwiseColor);
        mPaint.setStrokeWidth(2);

        canvas.drawLine(centerX, centerY, centerX, centerY - r + spacing, mPaint);

        if (mNum < 0) {
            mNum = -mNum;
        }

        if (mNum >= 12) {
            mNum = mNum % 12;
        }
        int angle = mNum * 30;
        double[] location = getXY(r - spacing, angle);
        if (mNum == 0) {
            return;
        } else {
            canvas.drawLine(centerX, centerY, centerX + (int) location[0], centerY - (int) location[1], mPaint);
        }
    }


    private double[] getXY(int hypotenuseLength, int angle) {
        if (angle < 0) {
            angle = angle + 360;
        }
        if (angle > 360) {
            angle = angle % 360;
        }
        double[] result = new double[2];
        double x = Math.sin(Math.toRadians(angle)) * hypotenuseLength;
        double y = Math.cos(Math.toRadians(angle)) * hypotenuseLength;
        result[0] = x;
        result[1] = y;
        return result;
    }

    public void setmNum(@Nullable int mNum) {
        this.mNum = mNum;
        invalidate();
    }

    public void setmClockColor(@ColorInt int mClockColor) {
        this.mClockColor = mClockColor;
        invalidate();
    }

    public void setmClockwiseColor(@ColorInt int mClockwiseColor) {
        this.mClockwiseColor = mClockwiseColor;
        invalidate();
    }
}
