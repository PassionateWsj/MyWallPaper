package com.jameswong.mywallpaper.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.adapter.GridViewAdapter;
import com.jameswong.mywallpaper.bean.Pictures;
import com.jameswong.mywallpaper.db.MyDataBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/12 9:43
 * name: 收藏界面
 * desc:
 * step:
 * *************************************************************
 */

public class CollectActivity extends Activity {

    @BindView(R.id.ptr_gv_activity_collect)
    PullToRefreshGridView mPtrGvActivityCollect;
    @BindView(R.id.btn_all_choose)
    Button mBtnAllChoose;
    @BindView(R.id.rl_edit_choose)
    RelativeLayout mRlEditChoose;

    private MyDataBase mMyDatabase;
    private SQLiteDatabase mReadableDatabase;
    private SQLiteDatabase mWritableDatabase;

    private List<Pictures.DataBean.WallpaperListInfoBean> mWallpaperListInfo;
    private boolean isManaging = false;
    private boolean isAllChoose = true;
    private GridViewAdapter mAdapter;
    private Map<Integer, Boolean> isSelected;

    private static final String TAG = "hjjzz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        initView();
        initDataBase();
        initAdapter();
        initData();
        setListener();
    }

    /**
     * 初始化View
     */
    private void initView() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        mMyDatabase = new MyDataBase(this);
        mReadableDatabase = mMyDatabase.getReadableDatabase();
        mWritableDatabase = mMyDatabase.getWritableDatabase();
    }

    /**
     * 设置适配器
     */
    private void initAdapter() {
        mAdapter = new GridViewAdapter(this, true);
        mPtrGvActivityCollect.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Cursor cursor = mReadableDatabase.query(MyDataBase.TABLE_NAME_COLLECT, null, null, null, null, null, MyDataBase.COLUMN_NAME_ID);
        if (cursor.moveToFirst()) {
            mWallpaperListInfo = new ArrayList<>();
            while (cursor.moveToNext()) {
                Pictures.DataBean.WallpaperListInfoBean wallpaperListInfoBean = new Pictures.DataBean.WallpaperListInfoBean();
                String PicName = cursor.getString(cursor.getColumnIndex(MyDataBase.COLUMN_NAME_WALLPAPER_NAME));
                String WallPaperMiddle = cursor.getString(cursor.getColumnIndex(MyDataBase.COLUMN_NAME_WALLPAPER_MIDDLE_URL));
                String WallPaperBig = cursor.getString(cursor.getColumnIndex(MyDataBase.COLUMN_NAME_WALLPAPER_BIG_URL));
                wallpaperListInfoBean.setPicName(PicName);
                wallpaperListInfoBean.setWallPaperMiddle(WallPaperMiddle);
                wallpaperListInfoBean.setWallPaperBig(WallPaperBig);
                mWallpaperListInfo.add(wallpaperListInfoBean);
            }
            mAdapter.setData(mWallpaperListInfo, true);
        }
    }

    /**
     * 设置监听器
     */
    private void setListener() {

        mPtrGvActivityCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isManaging) {
                    if (isSelected.get(position)) {
                        isSelected.put(position, false);
                        Log.i(TAG, "取消选中:::" + position);
                    } else {
                        isSelected.put(position, true);
                        Log.i(TAG, "选中:::" + position);
                        Log.i(TAG, "选中存入的url:::" + mWallpaperListInfo.get(position).getPicName());
                    }
                    mAdapter.setSelectedStatus(isSelected);
                } else {
                    Log.i(TAG, "Collect界面跳转item:::" + position);
                    Intent intent = new Intent(CollectActivity.this, ShowBigPictureActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("data", (Serializable) mWallpaperListInfo);
                    startActivity(intent);
                    Toast.makeText(CollectActivity.this, "点击的是第:::" + position + "个图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 管理按钮的监听
     *
     * @param view
     */
    public void collectManage(View view) {
        switch (view.getId()) {
            case R.id.btn_all_choose:
                if (isAllChoose) {
                    for (int i = 0; i < mWallpaperListInfo.size(); i++) {
                        isSelected.put(i, true);
                    }
                    mBtnAllChoose.setText(R.string.btn_none_choose);
                    isAllChoose = false;
                } else {
                    for (int i = 0; i < mWallpaperListInfo.size(); i++) {
                        isSelected.put(i, false);
                    }
                    mBtnAllChoose.setText(R.string.btn_all_choose);
                    isAllChoose = true;
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_delete:
                for (int i = 0; i < mWallpaperListInfo.size(); i++) {
                    if (isSelected.get(i)) {
                        mWritableDatabase.delete(MyDataBase.TABLE_NAME_COLLECT, MyDataBase.COLUMN_NAME_WALLPAPER_NAME + " = ?", new String[]{mWallpaperListInfo.get(i).getPicName()});
                        Log.i(TAG, "删除的id:::" + i);
                        Log.i(TAG, "删除的url:::" + mWallpaperListInfo.get(i).getPicName());
                    }
                }
                // 初始化选中状态的集合
                isSelected = new HashMap<>();
                for (int i = 0; i < mWallpaperListInfo.size(); i++) {
                    isSelected.put(i, false);
                }
                initData();
                mAdapter.setSelectedStatus(isSelected);
                break;
            default:
                // 初始化选中状态的集合
                isSelected = new HashMap<>();
                for (int i = 0; i < mWallpaperListInfo.size(); i++) {
                    isSelected.put(i, false);
                }
                // 处理是否在管理，并改变是否在管理的标志变量
                if (isManaging) {   // 原本在管理界面
                    mRlEditChoose.setVisibility(View.GONE);
                    isManaging = false;
                } else {    // 原本在浏览 界面
                    mRlEditChoose.setVisibility(View.VISIBLE);
                    isManaging = true;
                }
                mAdapter.setManageListener(isManaging);
                break;
        }
    }
}
