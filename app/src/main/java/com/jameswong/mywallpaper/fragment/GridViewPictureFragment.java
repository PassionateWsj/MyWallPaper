package com.jameswong.mywallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.activity.ShowBigPictureActivity;
import com.jameswong.mywallpaper.adapter.GridViewAdapter;
import com.jameswong.mywallpaper.bean.Pictures;
import com.jameswong.mywallpaper.constants.Constants;
import com.jameswong.mywallpaper.utils.LoadDataUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 19:21
 * name: 只显示GridView的Fragment
 * desc:
 * step:
 * *************************************************************
 */

public class GridViewPictureFragment extends Fragment {

    private String mBigid;
    private String mKeyWord;
    private String url;
    private int i = 1;
    private PullToRefreshGridView mPullToRefreshGridView;
    private GridViewAdapter mAdapter;

    private Pictures mPictures;
    private List<Pictures.DataBean.WallpaperListInfoBean> mWallpaperListInfo;

    private static final String TAG = "hjjzz";
    private ILoadingLayout mStartLabels;
    private int mDay;
    private int mLastDay;
    private boolean isRefresh = false;
    private boolean isSearchUrl = false;
    private int currentErrorTime = 0;

    public static GridViewPictureFragment getInstance(String keyWord, String id) {

        GridViewPictureFragment gridViewPictureFragment = new GridViewPictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyWord", keyWord);
        bundle.putString("id", id);
        gridViewPictureFragment.setArguments(bundle);
        return gridViewPictureFragment;
    }

    public static GridViewPictureFragment getInstance(String keyWord) {

        GridViewPictureFragment gridViewPictureFragment = new GridViewPictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyWord", keyWord);
        gridViewPictureFragment.setArguments(bundle);

        return gridViewPictureFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mKeyWord = arguments.getString("keyWord");
        mBigid = arguments.getString("id");
        if (mBigid == null) {
            isSearchUrl = true;
            try {
                mKeyWord = URLEncoder.encode(mKeyWord, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            isSearchUrl = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pictures, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        setListener();
    }

    /**
     * 初始化View
     *
     * @param view
     */
    private void initView(View view) {
        mWallpaperListInfo = new ArrayList<>();
        mPullToRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.ptr_gv_fragment_pic);
        mPullToRefreshGridView.setMode(BOTH);
        mAdapter = new GridViewAdapter(getActivity());
//        mPullToRefreshGridView.setAdapter(null);
        mPullToRefreshGridView.setAdapter(mAdapter);
        //自定义下拉指示器文本内容等效果
        initPullToRefreshText();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        if (isSearchUrl) {
            url = String.format(Constants.KEYWORDSEARCH, mKeyWord, i);
            System.out.println(getActivity());
        } else {
            url = String.format(Constants.FORMATPATH, mKeyWord, i, mBigid);
            if (mKeyWord.equals(Constants.RANDOM)) {
                url = String.format(Constants.RANDOMPATH, mBigid);
            }
        }

        LoadDataUtils.getInstance(getActivity()).getStringData(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mPictures = new Gson().fromJson(response, Pictures.class);
                if ("操作成功。".equals(mPictures.getMsg())) {
                    mAdapter.setData(mPictures.getData().getWallpaperListInfo(), isRefresh);
                    mPullToRefreshGridView.onRefreshComplete();
                    Log.i(TAG, "GridViewPictureFragment:::下载到GridLayout图片了" + response);
                    if (isRefresh) {
                        mWallpaperListInfo.clear();
                        mWallpaperListInfo = mPictures.getData().getWallpaperListInfo();
                    } else {
                        mWallpaperListInfo.addAll(mPictures.getData().getWallpaperListInfo());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPullToRefreshGridView.onRefreshComplete();
                Log.i(TAG, "GridViewPictureFragment:::onErrorResponse:::下载出错了");
                if (currentErrorTime < 3) {
                    initData();
                    currentErrorTime++;
                } else {
                    Toast.makeText(getActivity(), "抱歉，我无能为力...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        //GridView点击图片的监听
        mPullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowBigPictureActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("data", (Serializable) mWallpaperListInfo);
                startActivity(intent);
                Toast.makeText(getActivity(), "点击的是第:::" + position + "个图片", Toast.LENGTH_SHORT).show();
            }

        });

        //下拉刷新
        //上拉刷新
        mPullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                i = 1;
                isRefresh = true;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                i++;
                isRefresh = false;
                initData();
            }
        });
    }

    /**
     * 自定义下拉指示器文本内容等效果
     */
    private void initPullToRefreshText() {
        mStartLabels = mPullToRefreshGridView.getLoadingLayoutProxy();
        mStartLabels.setLastUpdatedLabel(dateFormat());
        mStartLabels.setPullLabel("你tm再拉试试看");
        mStartLabels.setRefreshingLabel("我...刷新");
        mStartLabels.setReleaseLabel("你**放了试试看");
    }

    /**
     * 格式化下拉刷新时间
     *
     * @return
     */
    private String dateFormat() {
        SimpleDateFormat simpleDateFormat;
        Date date = new Date();
        mDay = date.getDay();
        switch (mDay - mLastDay) {
            case 1:
                mLastDay = mDay;
                simpleDateFormat = new SimpleDateFormat("昨天a hh:mm:ss");
                return simpleDateFormat.format(date);
            case 0:
                mLastDay = mDay;
                simpleDateFormat = new SimpleDateFormat("a hh:mm:ss");
                return simpleDateFormat.format(date);
            case 2:
                mLastDay = mDay;
                simpleDateFormat = new SimpleDateFormat("前天a hh:mm:ss");
                return simpleDateFormat.format(date);
            default:
                break;
        }
        simpleDateFormat = new SimpleDateFormat("MM:dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
