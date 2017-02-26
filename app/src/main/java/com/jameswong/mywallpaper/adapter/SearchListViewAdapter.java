package com.jameswong.mywallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.activity.ContentActivity;
import com.jameswong.mywallpaper.view.SimpleProgressImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/10 11:18
 * name: 搜索界面，ListView的多类型Adapter
 * desc:
 * step:
 * *************************************************************
 */

public class SearchListViewAdapter extends BaseAdapter {
    private final DisplayImageOptions mRoundedOptions;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * HotSearch数据集
     */
    private List<com.jameswong.mywallpaper.bean.HotSearch.DataBean> mHotSearchData;
    /**
     * SearchMore数据集
     */
    private List<com.jameswong.mywallpaper.bean.SearchMore.DataBean.TopicBean> mSearchMoreData;
    /**
     * ThreePicture数据集
     */
    private List<com.jameswong.mywallpaper.bean.ThreePicture.DataBean> mThreePictureData;

    /**
     * 定义多类型
     */
    private static final int TYPE_HOTSEARCH = 0;
    private static final int TYPE_SEARCHMORE = 1;
    private static final int TYPE_THREEPICTURE = 2;
    private static final int TYPE_COUNT = TYPE_THREEPICTURE + 1;
    private final DisplayImageOptions mOptions;

    public SearchListViewAdapter(Context context) {
        mContext = context;
        //ImageLoader初始化
        mOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        mRoundedOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(16))
                .cacheInMemory(true)
                .build();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HOTSEARCH;
            case 1:
                return TYPE_SEARCHMORE;
        }
        return TYPE_THREEPICTURE;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return mHotSearchData == null || mSearchMoreData == null || mThreePictureData == null ? 0 : mThreePictureData.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        switch (getItemViewType(position)) {
            case TYPE_HOTSEARCH:
                return mHotSearchData;
            case TYPE_SEARCHMORE:
                return mSearchMoreData;
        }
        return mThreePictureData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case TYPE_HOTSEARCH:
                HotSearch mHotSearch = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hotsearch, parent, false);
                    mHotSearch = new HotSearch(convertView);
                    convertView.setTag(mHotSearch);
                } else {
                    mHotSearch = (HotSearch) convertView.getTag();
                }
                //下载图片
                loadHotSearchItemData(mHotSearch);
                mHotSearch.mIvHotSearchContainer1.setOnClickListener(mHotSearchItemListener);
                mHotSearch.mIvHotSearchContainer2.setOnClickListener(mHotSearchItemListener);
                mHotSearch.mIvHotSearchContainer3.setOnClickListener(mHotSearchItemListener);
                mHotSearch.mIvHotSearchContainer4.setOnClickListener(mHotSearchItemListener);
                return convertView;
            case TYPE_SEARCHMORE:
                SearchMore mSearchMore = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_searchmore, parent, false);
                    mSearchMore = new SearchMore(convertView);
                    convertView.setTag(mSearchMore);
                } else {
                    mSearchMore = (SearchMore) convertView.getTag();
                }
                // TODO: 设置ViewPager
                // TODO: 下载图片
                return convertView;
        }
        ThreePicture mThreePicture = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_threepicture, parent, false);
            mThreePicture = new ThreePicture(convertView);
            convertView.setTag(mThreePicture);
        } else {
            mThreePicture = (ThreePicture) convertView.getTag();
        }
        loadThreePictureData(position, mThreePicture);
        return convertView;
    }

    /**
     * 设置HotSearch事件监听
     */
    private View.OnClickListener mHotSearchItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ContentActivity.class);
            switch (v.getId()) {
                case R.id.iv_hot_search_container1:
                    intent.putExtra("PicCategoryName", mHotSearchData.get(0).getKeyword());
                    break;
                case R.id.iv_hot_search_container2:
                    intent.putExtra("PicCategoryName", mHotSearchData.get(1).getKeyword());
                    break;
                case R.id.iv_hot_search_container3:
                    intent.putExtra("PicCategoryName", mHotSearchData.get(2).getKeyword());
                    break;
                case R.id.iv_hot_search_container4:
                    intent.putExtra("PicCategoryName", mHotSearchData.get(3).getKeyword());
                    break;
            }
            mContext.startActivity(intent);
        }
    };

    /**
     * 下载ThreePicture的图片
     *
     * @param position
     * @param mThreePicture
     */
    private void loadThreePictureData(int position, ThreePicture mThreePicture) {
        mThreePicture.mTvThreePicture.setText(mThreePictureData.get(position - getViewTypeCount() + 1).getKeyword());
        mThreePicture.mIvThreePicture1.setImageUrl(mThreePictureData.get(position - getViewTypeCount() + 1).getImgs().get(0), mOptions);
        mThreePicture.mIvThreePicture2.setImageUrl(mThreePictureData.get(position - getViewTypeCount() + 1).getImgs().get(1), mOptions);
        mThreePicture.mIvThreePicture3.setImageUrl(mThreePictureData.get(position - getViewTypeCount() + 1).getImgs().get(2), mOptions);
    }

    /**
     * 下载HotSearch的图片
     *
     * @param mHotSearch
     */
    private void loadHotSearchItemData(HotSearch mHotSearch) {
        mHotSearch.mTvHotSearchContainer1.setText(mHotSearchData.get(0).getKeyword());
        mHotSearch.mTvHotSearchContainer2.setText(mHotSearchData.get(1).getKeyword());
        mHotSearch.mTvHotSearchContainer3.setText(mHotSearchData.get(2).getKeyword());
        mHotSearch.mTvHotSearchContainer4.setText(mHotSearchData.get(3).getKeyword());
        mHotSearch.mIvHotSearchContainer1.setImageUrl(mHotSearchData.get(0).getImgs().get(0), mRoundedOptions);
        mHotSearch.mIvHotSearchContainer2.setImageUrl(mHotSearchData.get(1).getImgs().get(0), mRoundedOptions);
        mHotSearch.mIvHotSearchContainer3.setImageUrl(mHotSearchData.get(2).getImgs().get(0), mRoundedOptions);
        mHotSearch.mIvHotSearchContainer4.setImageUrl(mHotSearchData.get(3).getImgs().get(0), mRoundedOptions);
    }

    /**
     * HotSearch滚动优化
     */
    class HotSearch {
        @BindView(R.id.iv_hot_search_container1)
        SimpleProgressImageView mIvHotSearchContainer1;
        @BindView(R.id.iv_hot_search_container2)
        SimpleProgressImageView mIvHotSearchContainer2;
        @BindView(R.id.iv_hot_search_container3)
        SimpleProgressImageView mIvHotSearchContainer3;
        @BindView(R.id.iv_hot_search_container4)
        SimpleProgressImageView mIvHotSearchContainer4;
        @BindView(R.id.tv_hot_search_container1)
        TextView mTvHotSearchContainer1;
        @BindView(R.id.tv_hot_search_container2)
        TextView mTvHotSearchContainer2;
        @BindView(R.id.tv_hot_search_container3)
        TextView mTvHotSearchContainer3;
        @BindView(R.id.tv_hot_search_container4)
        TextView mTvHotSearchContainer4;

        public HotSearch(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * SearchMore滚动优化
     */
    class SearchMore {
        @BindView(R.id.vp_search_more)
        ViewPager mVpSearchMore;
        @BindView(R.id.ll_search_more_img_home_select)
        LinearLayout mLlSearchMoreImgHomeSelect;

        public SearchMore(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * ThreePicture滚动优化
     */
    class ThreePicture {
        @BindView(R.id.tv_three_picture)
        TextView mTvThreePicture;
        @BindView(R.id.iv_three_picture1)
        SimpleProgressImageView mIvThreePicture1;
        @BindView(R.id.iv_three_picture2)
        SimpleProgressImageView mIvThreePicture2;
        @BindView(R.id.iv_three_picture3)
        SimpleProgressImageView mIvThreePicture3;

        public ThreePicture(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 暴露方法为HotSearch添加数据
     *
     * @param hotSearchData
     */
    public void setHotSearchData(List<com.jameswong.mywallpaper.bean.HotSearch.DataBean> hotSearchData) {
        if (mHotSearchData == null) {
            mHotSearchData = new ArrayList<>();
        }
        mHotSearchData = hotSearchData;
        notifyDataSetChanged();
    }

    /**
     * 暴露方法为SearchMore添加数据
     *
     * @param topicData
     */
    public void setSearchMoreData(List<com.jameswong.mywallpaper.bean.SearchMore.DataBean.TopicBean> topicData) {
        if (mSearchMoreData == null) {
            mSearchMoreData = new ArrayList<>();
        }
        mSearchMoreData = topicData;
        notifyDataSetChanged();
    }

    /**
     * 暴露方法为ThreePicture添加数据
     *
     * @param threePictureData
     */
    public void setThreePictureData(List<com.jameswong.mywallpaper.bean.ThreePicture.DataBean> threePictureData) {
        if (mThreePictureData == null) {
            mThreePictureData = new ArrayList<>();
        }
        mThreePictureData = threePictureData;
        notifyDataSetChanged();
    }
}
