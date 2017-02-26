package com.jameswong.mywallpaper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 19:19
 * name: 显示网格图片3个Fragment的适配器
 * desc:
 * step:
 * *************************************************************
 */
public class WallPaperGridViewAdapter extends FragmentPagerAdapter {
    /**
     * Fragment数据源
     */
    private List<Fragment> mFragmentList;
    private String[] mTabTitle;

    public WallPaperGridViewAdapter(FragmentManager fm, List<Fragment> mFragmentList, String[] mTabTitle) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mTabTitle = mTabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }
}
