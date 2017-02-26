package com.jameswong.mywallpaper.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/09 15:59
 * name: Application创建的时候初始化ImageLoader
 * desc:
 * step:
 * *************************************************************
 */

public class IApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
