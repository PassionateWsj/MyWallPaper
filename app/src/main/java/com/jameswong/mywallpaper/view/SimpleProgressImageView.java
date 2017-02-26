package com.jameswong.mywallpaper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 带下载和进度显示自定义控件
 */
public class SimpleProgressImageView extends RelativeLayout {

    private ImageView mImageView;
    private SimpleProgressBar mSimpleProgressBar;
    private ImageLoader mImageLoader;

    public SimpleProgressImageView(Context context) {
        this(context, null);
    }

    public SimpleProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mImageLoader = ImageLoader.getInstance();
        initViews();
    }

    private void initViews() {
        mImageView = new ImageView(getContext());
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mImageView);
        mSimpleProgressBar = new SimpleProgressBar(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mSimpleProgressBar.setLayoutParams(lp);
        addView(mSimpleProgressBar);
    }

    public void setImageUrl(String url, DisplayImageOptions options) {
        resetView();
        mImageLoader.displayImage(url, mImageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mSimpleProgressBar.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                mSimpleProgressBar.setProgress(1f * current / total);
            }
        });
    }

    /**
     * 重置
     */
    private void resetView() {
        mImageView.setImageDrawable(null);
        mSimpleProgressBar.setVisibility(View.VISIBLE);
        mSimpleProgressBar.setProgress(0);
    }
}
