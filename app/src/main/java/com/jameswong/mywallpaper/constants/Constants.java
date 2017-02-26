package com.jameswong.mywallpaper.constants;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 19:07
 * name: URL接口
 * desc:
 * step:
 * *************************************************************
 */

public class Constants {
    //3种类型
    public static String WALLPAPERNEW = "wallPaperNew";
    public static String HOTRECENT = "hotRecent";
    public static String RANDOM = "random";
    //推荐、热门的url
    public static String FORMATPATH = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=%s&index=%d&size=60&bigid=%s";
    //随机的url
    public static String RANDOMPATH = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid=%s";

    //关键字搜索图片集
    public static String KEYWORDSEARCH = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=search&q=%s&p=%d&s=30";
    //热门搜索
    public static String HOTSEARCH = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=hot&location=1";
    //查看更多、详情图片集
    public static String SEARCHMORE = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=list&topictype=2&size=10";
    public static String TOPICIDSEARCH = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=detail&index=%d&size=9&typeid=2&topicid=%s";
    //3张图片集合
    public static String THREEPICTURE = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=hot&location=3";


}
