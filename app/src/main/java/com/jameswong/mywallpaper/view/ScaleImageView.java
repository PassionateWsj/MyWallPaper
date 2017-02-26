package com.jameswong.mywallpaper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * 1.宽度不变，改变高度使尺寸和屏幕尺寸相同
 * 2.存在下载功能和进度显示的ImageView
 */
public class ScaleImageView extends SimpleProgressImageView {
    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = width * displayMetrics.heightPixels / displayMetrics.widthPixels;
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)//
                , MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }
}
