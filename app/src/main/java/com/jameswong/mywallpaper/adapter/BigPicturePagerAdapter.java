package com.jameswong.mywallpaper.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.bean.Pictures;
import com.jameswong.mywallpaper.view.ScaleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/09 15:42
 * name: 大图的适配器
 * desc:
 * step:
 * *************************************************************
 */

public class BigPicturePagerAdapter extends PagerAdapter {
    private List<Pictures.DataBean.WallpaperListInfoBean> mWallpaperListInfo;
    private List<View> mViewList;
    private ScaleImageView mScaleImageView;
    private static final String TAG = "hjjzz";
    private final DisplayImageOptions mOptions;

    public BigPicturePagerAdapter(List<View> mViewList, List<Pictures.DataBean.WallpaperListInfoBean> wallpaperListInfo) {
        this.mViewList = mViewList;
        this.mWallpaperListInfo = wallpaperListInfo;
        /**
         * 设置图片显示的样式
         */
        mOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mViewList.get(position));
        mScaleImageView = (ScaleImageView) mViewList.get(position).findViewById(R.id.iv_picture);

        mScaleImageView.setImageUrl(mWallpaperListInfo.get(position).getWallPaperBig(),mOptions);
        Log.i(TAG, "BigPicturePagerAdapter:::instantiateItem" + position);
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
        Log.i(TAG, "BigPicturePagerAdapter:::destroyItem" + position);
    }
}
