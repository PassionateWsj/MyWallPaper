package com.jameswong.mywallpaper.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jameswong.mywallpaper.R;


/**
 * 一个简单的进度条
 */
public class SimpleProgressBar extends View {

    private int mMax = 100;
    private int mProgress = 0;
    private int mRadius;
    private int mStrokeWidth = 6;
    private RectF mRectF;
    private Paint mPaint;
    private int mDefaultSize;

    public SimpleProgressBar(Context context) {
        this(context, null);
    }

    public SimpleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //进度条的默认大小
        mDefaultSize = (int) getResources().getDimension(R.dimen.ProgressBarSize);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = mDefaultSize;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY)//
                , MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY));
        mRadius = widthSize / 2;
        mRectF = new RectF(mStrokeWidth / 2, mStrokeWidth / 2, widthSize - mStrokeWidth / 2, widthSize - mStrokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(0xFF999999);
        canvas.drawCircle(mRadius, mRadius, mRadius - mStrokeWidth / 2, mPaint);
        mPaint.setColor(0xFF0ABF4C);
        canvas.drawArc(mRectF, -90, 360 * mProgress / mMax, false, mPaint);
    }

    public int getMax() {
        return mMax;
    }


    public void setProgress(int progress) {
        mProgress = progress < 0 ? 0 : (progress > mMax ? mMax : progress);
        postInvalidate();
    }

    public void setProgress(float percent) {
        float p = percent < 0 ? 0 : (percent > 1 ? 1 : percent);
        setProgress((int) (mMax * p));
    }


    public int getProgress() {
        return mProgress;
    }
}
