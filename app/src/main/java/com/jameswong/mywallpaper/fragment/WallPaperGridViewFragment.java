package com.jameswong.mywallpaper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.adapter.WallPaperGridViewAdapter;
import com.jameswong.mywallpaper.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 14:59
 * name: 第一个界面的可复用Fragment
 * desc:
 * step:
 * *************************************************************
 */

public class WallPaperGridViewFragment extends Fragment {

    private String mPicCategoryName;
    private String mId;
    private TextView mTitle;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<Fragment> mFragmentList;
    private boolean hasTabLayout = true;

    public static WallPaperGridViewFragment getInstance(String picCategoryName, String id) {
        WallPaperGridViewFragment fragment = new WallPaperGridViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PicCategoryName", picCategoryName);
        bundle.putString("ID", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WallPaperGridViewFragment getInstance(String picCategoryName) {
        WallPaperGridViewFragment fragment = new WallPaperGridViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PicCategoryName", picCategoryName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mPicCategoryName = arguments.getString("PicCategoryName");
        mId = arguments.getString("ID");
        if (mId == null) {
            hasTabLayout = false;
        } else {
            hasTabLayout = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallpaper, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initAdapter();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mTitle.setText(mPicCategoryName);
        if (hasTabLayout) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    /**
     * 初始化View
     *
     * @param view
     */
    private void initView(View view) {
        mTitle = (TextView) view.findViewById(R.id.tv_wallpaper_title);
        mTabLayout = (TabLayout) view.findViewById(R.id.tl_fragment_wallpaper);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment_wallpaper);
        mFragmentList = new ArrayList<>();
        if (hasTabLayout) {
            mViewPager.setOffscreenPageLimit(2);
            mFragmentList.add(GridViewPictureFragment.getInstance(Constants.WALLPAPERNEW, mId));
            mFragmentList.add(GridViewPictureFragment.getInstance(Constants.HOTRECENT, mId));
            mFragmentList.add(GridViewPictureFragment.getInstance(Constants.RANDOM, mId));
        } else {
            mTabLayout.setVisibility(View.GONE);
            mFragmentList.add(GridViewPictureFragment.getInstance(mPicCategoryName));
        }
            mAdapter = new WallPaperGridViewAdapter(getFragmentManager(), mFragmentList, getResources().getStringArray(R.array.TabTitle));
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mViewPager.setAdapter(mAdapter);
    }
}
