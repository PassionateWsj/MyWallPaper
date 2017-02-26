package com.jameswong.mywallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.activity.ContentActivity;
import com.jameswong.mywallpaper.adapter.SearchListViewAdapter;
import com.jameswong.mywallpaper.bean.HotSearch;
import com.jameswong.mywallpaper.bean.SearchMore;
import com.jameswong.mywallpaper.bean.ThreePicture;
import com.jameswong.mywallpaper.constants.Constants;
import com.jameswong.mywallpaper.utils.LoadDataUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_ENTER;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 13:26
 * name: 搜索界面的Fragment
 * desc:
 * step:
 * *************************************************************
 */

public class SearchFragment extends Fragment {

    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.lv_search)
    ListView mLvSearch;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    private SearchListViewAdapter mSearchListViewAdapter;
    private HotSearch mHotSearch;
    private SearchMore mSearchMore;
    private ThreePicture mThreePicture;
    private int mDownLoadHotSearchDataErrorTime;
    private int mDownLoadSearchMoreDataErrorTime;
    private int mDownLoadThreePictureDataErrorTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化butterKnife，findViewByID框架
        ButterKnife.bind(this, view);
        //初始化数据
        initData();
        //初始化适配器
        initAdapter();
        //设置事件监听
        setListener();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        //搜索Button的监听
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchAvtivity();
            }
        });
        //EditText回车监听
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KEYCODE_ENTER) {
                    startSearchAvtivity();
                }
                return true;
            }
        });
        //ListView的监听
        mLvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 && position != 1 && mThreePicture != null) {
                    Toast.makeText(getActivity(), "点击是" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ContentActivity.class);
                    intent.putExtra("PicCategoryName", mThreePicture.getData().get(position - 2).getKeyword());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 实现搜索跳转
     */
    private void startSearchAvtivity() {
        String searchKeyWord = mEtSearch.getText().toString().trim();
        Toast.makeText(getActivity(), "搜索" + searchKeyWord, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(searchKeyWord)) {
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            intent.putExtra("PicCategoryName", searchKeyWord);
            mEtSearch.setText("");
            startActivity(intent);
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mSearchListViewAdapter = new SearchListViewAdapter(getActivity());
        mLvSearch.setAdapter(mSearchListViewAdapter);
    }

    /**
     * 初始化数据
     */
    private synchronized void initData() {
        //下载HotSearch数据
        synchronized (getActivity()) {
            if (mHotSearch == null || !"操作成功。".equals(mHotSearch.getCode())) {
                LoadDataUtils.getInstance(getActivity()).getStringData(Request.Method.GET, Constants.HOTSEARCH, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mHotSearch = new Gson().fromJson(response, HotSearch.class);
                        mSearchListViewAdapter.setHotSearchData(mHotSearch.getData());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "HotSearch下载出错了", Toast.LENGTH_SHORT).show();
                        if (mDownLoadHotSearchDataErrorTime < 3) {
                            initData();
                            mDownLoadHotSearchDataErrorTime++;
                        }
                    }
                });
            }
        }
        //下载SearchMore数据
        synchronized (getActivity()) {
            if (mSearchMore == null || !"操作成功。".equals(mSearchMore.getCode())) {
                LoadDataUtils.getInstance(getActivity()).getStringData(Request.Method.GET, Constants.SEARCHMORE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mSearchMore = new Gson().fromJson(response, SearchMore.class);
                        mSearchListViewAdapter.setSearchMoreData(mSearchMore.getData().getTopic());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "SearchMore下载出错了", Toast.LENGTH_SHORT).show();
                        if (mDownLoadSearchMoreDataErrorTime < 3) {
                            initData();
                            mDownLoadSearchMoreDataErrorTime++;
                        }
                    }
                });
            }
        }
        //下载ThreePicture数据
        synchronized (getActivity()) {
            if (mThreePicture == null || !"操作成功。".equals(mThreePicture.getCode())) {
                LoadDataUtils.getInstance(getActivity()).getStringData(Request.Method.GET, Constants.THREEPICTURE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mThreePicture = new Gson().fromJson(response, ThreePicture.class);
                        mSearchListViewAdapter.setThreePictureData(mThreePicture.getData());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "ThreePicture下载出错了", Toast.LENGTH_SHORT).show();
                        if (mDownLoadThreePictureDataErrorTime < 3) {
                            initData();
                            mDownLoadThreePictureDataErrorTime++;
                        }
                    }
                });
            }
        }
    }
}
