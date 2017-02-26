package com.jameswong.mywallpaper.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 16:37
 * name: Volley单例模式的工具类
 * desc:
 * step:
 * 1. 创建出来一个私有类的成员变量
 * 2. 构造方法私有化
 * 3. 暴露出一个公共的方法，返回当前对象
 * *************************************************************
 */

public class LoadDataUtils {
    private static LoadDataUtils mLoadDataUtils;
    private final RequestQueue mRequestQueue;

    private LoadDataUtils(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static LoadDataUtils getInstance(Context context) {
        if (mLoadDataUtils == null) {
            synchronized (LoadDataUtils.class) {
                if (mLoadDataUtils == null) {
                    mLoadDataUtils = new LoadDataUtils(context);
                }
            }
        }
        return mLoadDataUtils;
    }

    public void getStringData(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(method, url, listener, errorListener);
        mRequestQueue.add(request);
    }
}
