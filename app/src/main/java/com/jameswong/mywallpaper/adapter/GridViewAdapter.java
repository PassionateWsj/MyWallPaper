package com.jameswong.mywallpaper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.bean.Pictures;
import com.jameswong.mywallpaper.view.ScaleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 19:31
 * name: 显示网格图片GridView的适配器
 * desc:
 * step:
 * *************************************************************
 */
public class GridViewAdapter extends BaseAdapter {

    private List<Pictures.DataBean.WallpaperListInfoBean> mWallpaperListInfoBeanList;
    private Context mContext;
    private final DisplayImageOptions mOptions;
    private boolean isManageType = false;
    private boolean isManaging = false;
    private static Map<Integer,Boolean> isSelected;

    public GridViewAdapter(Context context) {
        mContext = context;

        mOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }

    public GridViewAdapter(Context context, boolean isManageType) {
        this(context);
        this.isManageType = isManageType;
    }


    @Override
    public int getCount() {
        return mWallpaperListInfoBeanList == null ? 0 : mWallpaperListInfoBeanList.size();
    }

    @Override
    public Pictures.DataBean.WallpaperListInfoBean getItem(int position) {
        return mWallpaperListInfoBeanList == null ? null : mWallpaperListInfoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isManageType) {
            CollectViewHolder mCollectViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collect_gridview, parent, false);
                mCollectViewHolder = new CollectViewHolder(convertView);
                convertView.setTag(mCollectViewHolder);
            } else {
                mCollectViewHolder = (CollectViewHolder) convertView.getTag();
            }
            /**
             * 设置图片显示的样式
             */
            if (isManaging) {
                mCollectViewHolder.mCbPic.setVisibility(View.VISIBLE);
                mCollectViewHolder.mPicBackground.setVisibility(View.VISIBLE);
                if (isSelected != null) {
                    if (isSelected.get(position)) {
                        mCollectViewHolder.mCbPic.setImageResource(R.drawable.select_item_checked);
                    } else {
                        mCollectViewHolder.mCbPic.setImageResource(R.drawable.select_item_unchecked);
                    }
                }
            } else {
                mCollectViewHolder.mCbPic.setVisibility(View.GONE);
                mCollectViewHolder.mPicBackground.setVisibility(View.GONE);
            }
            mCollectViewHolder.mIvGridview.setImageUrl(mWallpaperListInfoBeanList.get(position).getWallPaperMiddle(), mOptions);

            return convertView;
        }
        CommonViewHolder mCommonViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview, parent, false);
            mCommonViewHolder = new CommonViewHolder(convertView);
            convertView.setTag(mCommonViewHolder);
        } else {
            mCommonViewHolder = (CommonViewHolder) convertView.getTag();
        }
        /**
         * 设置图片显示的样式
         */
        mCommonViewHolder.mScaleImageView.setImageUrl(mWallpaperListInfoBeanList.get(position).getWallPaperMiddle(), mOptions);

        return convertView;
    }

    /**
     * 普通界面Holder
     */
    class CommonViewHolder {
        private ScaleImageView mScaleImageView;

        public CommonViewHolder(View itemView) {
            mScaleImageView = (ScaleImageView) itemView.findViewById(R.id.iv_gridview);

        }
    }

    /**
     * 收集界面的Holder
     */
    static class CollectViewHolder {
        @BindView(R.id.pic_background)
        ImageView mPicBackground;
        @BindView(R.id.cb_pic)
        ImageView mCbPic;
        @BindView(R.id.iv_gridview_collect)
        ScaleImageView mIvGridview;

        CollectViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 为GridView适配器设置数据源
     *
     * @param wallpaperListInfoBeanList
     * @param isRefresh
     */
    public void setData(List<Pictures.DataBean.WallpaperListInfoBean> wallpaperListInfoBeanList, boolean isRefresh) {
        if (mWallpaperListInfoBeanList == null) {
            mWallpaperListInfoBeanList = new ArrayList<>();
        }
        if (isRefresh) {
            mWallpaperListInfoBeanList.clear();
        }
        mWallpaperListInfoBeanList.addAll(wallpaperListInfoBeanList);
//        notifyDataSetInvalidated();
        // 提醒适配器重新刷新界面
        notifyDataSetChanged();
    }

    /**
     * 判断是否是收藏界面的管理界面
     *
     * @param toManaging
     */
    public void setManageListener(boolean toManaging) {
        if (isManaging != toManaging) {
            isManaging = toManaging;
        }
        notifyDataSetChanged();
    }

    public void setSelectedStatus(Map<Integer,Boolean> setSelected) {
        if (isSelected == null) {
            isSelected = new HashMap<>();
        }
        isSelected = setSelected;
        // 提醒适配器重新刷新界面
        notifyDataSetChanged();
    }

}
