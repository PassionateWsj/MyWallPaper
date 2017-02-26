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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.activity.ContentActivity;
import com.jameswong.mywallpaper.adapter.CategoryAdapter;
import com.jameswong.mywallpaper.bean.Category;
import com.jameswong.mywallpaper.utils.LoadDataUtils;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 13:26
 * name: 分类界面的Fragment
 * desc:
 * step:
 * *************************************************************
 */

public class CategoryFragment extends Fragment {

    private String url = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=category";
    private ListView mLv_fragment_category;
    private CategoryAdapter mCategoryAdapter;
    private static final String TAG = "hjjzz";
    private Category mCategory;
    private int currentErrorTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
        mLv_fragment_category = (ListView) view.findViewById(R.id.lv_fragment_category);
        mCategoryAdapter = new CategoryAdapter(getActivity());
        mLv_fragment_category.setAdapter(mCategoryAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        mLv_fragment_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "点击的是第" + position + "条", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("PicCategoryName", mCategory.getData().get(position).getPicCategoryName());
                intent.putExtra("ID", mCategory.getData().get(position).getID());
                startActivity(intent);
            }
        });
    }

    private synchronized void initData() {
        LoadDataUtils.getInstance(getActivity()).getStringData(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    mCategory = new Gson().fromJson(response, Category.class);
                    mCategoryAdapter.setData(mCategory.getData());
                    Log.i(TAG, "CategoryFragment:::返回的json数据为" + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "CategoryFragment:::出错了", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "CategoryFragment:::出错了");
                if (currentErrorTime < 3) {
                    initData();
                    currentErrorTime++;
                }
            }
        });
    }

}
