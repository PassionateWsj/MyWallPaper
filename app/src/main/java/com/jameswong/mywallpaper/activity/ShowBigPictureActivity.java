package com.jameswong.mywallpaper.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.adapter.BigPicturePagerAdapter;
import com.jameswong.mywallpaper.bean.Pictures;
import com.jameswong.mywallpaper.db.MyDataBase;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/09 10:52
 * name: 显示一张图片的Activity
 * desc:
 * step:
 * *************************************************************
 */

public class ShowBigPictureActivity extends Activity {

    private List<Pictures.DataBean.WallpaperListInfoBean> mWallpaperListInfo;
    private static final String TAG = "ShowBigPictureActivity";
    private Animation mAnimationUp;
    private Animation mAnimationDown;
    private LinearLayout mLl_show_top;
    private LinearLayout mLl_show_bottom;
    private ViewPager mVp_show_picture;
    private BigPicturePagerAdapter mBigPicturePagerAdapter;
    private List<View> mViewList;
    private int mPosition;
    private View mView;
    private MyDataBase mMyDataBase;
    private SQLiteDatabase mMyDataBaseWrite;
    private int mCurrentItem;
    private ContentValues mValues;
    private SQLiteDatabase mMyDataBaseRead;
    private Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);

        //隐藏状态栏
        hideStatusBar();

        initDataBase();
        initAnim();
        initView();
        initData();
        initAdapter();
        setListener();
    }

    private void initDataBase() {
        mMyDataBase = new MyDataBase(this);
        mMyDataBaseWrite = mMyDataBase.getWritableDatabase();
        mMyDataBaseRead = mMyDataBase.getReadableDatabase();
    }

    /**
     * 隐藏状态栏
     */
    private void hideStatusBar() {
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = ShowBigPictureActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }

    /**
     * 初始化View
     */
    private void initView() {
        mLl_show_top = (LinearLayout) findViewById(R.id.ll_show_top);
        mLl_show_bottom = (LinearLayout) findViewById(R.id.ll_show_bottom);
        mVp_show_picture = (ViewPager) findViewById(R.id.vp_show_picture);
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mAnimationUp = AnimationUtils.loadAnimation(this, R.anim.anim_show_up);
        mAnimationDown = AnimationUtils.loadAnimation(this, R.anim.anim_show_down);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //拿到position
        mPosition = getIntent().getIntExtra("position", -1);
        Log.i(TAG, "ShowPictureActicvity拿到的position:::" + mPosition);
        //拿到图片url对象集合
        mWallpaperListInfo = (List<Pictures.DataBean.WallpaperListInfoBean>) (getIntent().getSerializableExtra("data"));
        Log.i(TAG, "ShowPictureActicvity拿到的数据:::" + mWallpaperListInfo.size());
        mViewList = new ArrayList<>();
        for (int i = 0; i < mWallpaperListInfo.size(); i++) {
            mView = LayoutInflater.from(this).inflate(R.layout.item_picture, null);
            mViewList.add(mView);
        }
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        mBigPicturePagerAdapter = new BigPicturePagerAdapter(mViewList, mWallpaperListInfo);
        mVp_show_picture.setAdapter(mBigPicturePagerAdapter);
        mVp_show_picture.setCurrentItem(mPosition);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //动画监听
        animListener();
        //判断是否滑到底
        mVp_show_picture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Toast.makeText(ShowBigPictureActivity.this, "往前没有了", Toast.LENGTH_SHORT).show();
                }
                if (position == mWallpaperListInfo.size()) {
                    Toast.makeText(ShowBigPictureActivity.this, "这是最后一页", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 动画监听
     */
    private void animListener() {
        mAnimationUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimationDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 按钮的点击事件
     *
     * @param view
     */
    public void show_bottom_click(View view) {
        switch (view.getId()) {
            case R.id.vp_show_picture:
                // TODO: 显示、隐藏上下LinearLayout
                mLl_show_top.setAnimation(mAnimationUp);
                mLl_show_bottom.setAnimation(mAnimationDown);
                break;
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.ibtn_share:
                Toast.makeText(this, "分享了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_collect:
                mValues = new ContentValues();
                mCurrentItem = mVp_show_picture.getCurrentItem();
                mCursor = mMyDataBaseRead.query(MyDataBase.TABLE_NAME_COLLECT, null, MyDataBase.COLUMN_NAME_WALLPAPER_NAME + "= ?", new String[]{mWallpaperListInfo.get(mCurrentItem).getPicName()}, null, null, null);
                if (mCursor.getCount() == 0) {
                    mValues.put(MyDataBase.COLUMN_NAME_WALLPAPER_NAME, mWallpaperListInfo.get(mCurrentItem).getPicName());
                    mValues.put(MyDataBase.COLUMN_NAME_WALLPAPER_MIDDLE_URL, mWallpaperListInfo.get(mCurrentItem).getWallPaperMiddle());
                    mValues.put(MyDataBase.COLUMN_NAME_WALLPAPER_BIG_URL, mWallpaperListInfo.get(mCurrentItem).getWallPaperBig());
                    mMyDataBaseWrite.insert(MyDataBase.TABLE_NAME_COLLECT, null, mValues);
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "之前收藏过了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_set_wallpaper:
                //设置壁纸
                ImageLoader.getInstance().loadImage(mWallpaperListInfo.get(mVp_show_picture.getCurrentItem()).getWallPaperBig(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        Toast.makeText(ShowBigPictureActivity.this, "壁纸下载中", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        try {
                            ShowBigPictureActivity.this.setWallpaper(loadedImage);
                            Toast.makeText(ShowBigPictureActivity.this, "已经设置壁纸", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
                break;
            case R.id.btn_set_download:
                mValues = new ContentValues();
                mCurrentItem = mVp_show_picture.getCurrentItem();
                mCursor = mMyDataBaseRead.query(MyDataBase.TABLE_NAME_DOWNLOADED, null, MyDataBase.COLUMN_NAME_WALLPAPER_NAME + "= ?", new String[]{mWallpaperListInfo.get(mCurrentItem).getPicName()}, null, null, null);
                if (mCursor.getCount() == 0) {
                    mValues.put(MyDataBase.COLUMN_NAME_WALLPAPER_NAME, mWallpaperListInfo.get(mCurrentItem).getPicName());
                    mValues.put(MyDataBase.COLUMN_NAME_WALLPAPER_MIDDLE_URL, mWallpaperListInfo.get(mCurrentItem).getWallPaperMiddle());
                    mValues.put(MyDataBase.COLUMN_NAME_WALLPAPER_BIG_URL, mWallpaperListInfo.get(mCurrentItem).getWallPaperBig());
                    mValues.put(MyDataBase.COLUMN_NAME_DOWNLOAD_WALLPAPER_PATH, "");
                    mMyDataBaseWrite.insert(MyDataBase.TABLE_NAME_DOWNLOADED, null, mValues);
                    Toast.makeText(this, "下载中", Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "之前下载过了", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyDataBase.close();
        mMyDataBaseRead.close();
        mMyDataBaseWrite.close();
    }
}
