package com.jameswong.mywallpaper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/11 19:59
 * name:
 * desc:
 * step:
 * *************************************************************
 */

public class MyDataBase extends SQLiteOpenHelper {

    /**
     * 表名：收藏列表
     */
    public static final String TABLE_NAME_COLLECT = "collect";
    /**
     * 表名：下载列表
     */
    public static final String TABLE_NAME_DOWNLOADED = "downloaded";


    /**
     * 列名：_id
     */
    public static final String COLUMN_NAME_ID = "_id";
    /**
     * 列名：中图的URL
     */
    public static final String COLUMN_NAME_WALLPAPER_MIDDLE_URL = "url_middle_pic";
    /**
     * 列名：大图的URL
     */
    public static final String COLUMN_NAME_WALLPAPER_BIG_URL = "url_big_pic";
    /**
     * 列名：已下载列表图片对应的名字
     */
    public static final String COLUMN_NAME_WALLPAPER_NAME = "pic_name";
    /**
     * 列名：下载列表图片保存的路径
     */
    public static final String COLUMN_NAME_DOWNLOAD_WALLPAPER_PATH = "path";


    /**
     * 创建收藏的表
     */
    private String mCreateCollectTable = "CREATE TABLE " + TABLE_NAME_COLLECT + "(" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_WALLPAPER_NAME + " TEXT NOT NULL," +
            COLUMN_NAME_WALLPAPER_MIDDLE_URL + " TEXT NOT NULL," +
            COLUMN_NAME_WALLPAPER_BIG_URL + " TEXT NOT NULL" +
            ")";
    /**
     * 创建下载的表
     */
    private String mCreateDownLoadTable = "CREATE TABLE " + TABLE_NAME_DOWNLOADED + "(" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_WALLPAPER_NAME + " TEXT NOT NULL," +
            COLUMN_NAME_WALLPAPER_MIDDLE_URL + " TEXT NOT NULL," +
            COLUMN_NAME_WALLPAPER_BIG_URL + " TEXT NOT NULL," +
            COLUMN_NAME_DOWNLOAD_WALLPAPER_PATH + " TEXT NOT NULL" +
            ")";


    public MyDataBase(Context context) {
        super(context, "wallpaper_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 创建收藏的表
        db.execSQL(mCreateCollectTable);

        // 创建下载的表
        db.execSQL(mCreateDownLoadTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
