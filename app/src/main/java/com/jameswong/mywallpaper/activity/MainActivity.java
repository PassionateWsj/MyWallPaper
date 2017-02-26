package com.jameswong.mywallpaper.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.fragment.CategoryFragment;
import com.jameswong.mywallpaper.fragment.MoreFragment;
import com.jameswong.mywallpaper.fragment.SearchFragment;
import com.jameswong.mywallpaper.fragment.WallPaperGridViewFragment;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面Activity
 */
public class MainActivity extends FragmentActivity {
    /**
     * 导航栏的标题
     */
    private LinearLayout mLl_main_tab;
    private LinearLayout[] mLl_main_tab_content;
    private TextView[] mTab_text;
    private ImageView[] mTab_img;
    private int[] img_unchosen = {
            R.mipmap.bottom_recommand,
            R.mipmap.bottom_category,
            R.mipmap.bottom_search,
            R.mipmap.bottom_more
    };
    private int[] img_chosen = {
            R.mipmap.bottom_recommand_selected,
            R.mipmap.bottom_category_selected,
            R.mipmap.bottom_search_selected,
            R.mipmap.bottom_more_selected
    };
    /**
     * 标题文本信息
     */
    private String[] mTabTitle;
    /**
     * Fragment的集合
     */
    private List<Fragment> mFragments;
    private CategoryFragment mCategoryFragment;
    private SearchFragment mSearchFragment;
    private MoreFragment mMoreFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mCurrentFragment;
    private WallPaperGridViewFragment mRecommandFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
        initData();
    }

    private void initView() {
        mLl_main_tab = (LinearLayout) findViewById(R.id.ll_main_tab);
        mTabTitle = getResources().getStringArray(R.array.MainTab);
        mFragments = new ArrayList<>();
        mCategoryFragment = new CategoryFragment();
        mSearchFragment = new SearchFragment();
        mMoreFragment = new MoreFragment();
        mRecommandFragment = WallPaperGridViewFragment.getInstance("壁纸精选", 0 + "");
        mFragments.add(mRecommandFragment);
        mFragments.add(mCategoryFragment);
        mFragments.add(mSearchFragment);
        mFragments.add(mMoreFragment);
        mFragmentManager = getSupportFragmentManager();
        setChosenFragment(mFragments.get(0));
    }

    private void setChosenFragment(Fragment fragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (mCurrentFragment == null) {
            mFragmentTransaction.add(R.id.fragment_container, fragment).commit();
            mCurrentFragment = fragment;
        } else if (mCurrentFragment != fragment) {
            if (!fragment.isAdded()) {
                mFragmentTransaction.hide(mCurrentFragment).add(R.id.fragment_container, fragment).commit();
            } else {
                mFragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
            }
            mCurrentFragment = fragment;
        }
    }

    private void setListener() {
    }

    private void initData() {
        initTabData();
//        ImageLoader.getInstance().init(getSimpleConfig());
    }

    private void initTabData() {
        mLl_main_tab_content = new LinearLayout[mTabTitle.length];
        mTab_text = new TextView[mTabTitle.length];
        mTab_img = new ImageView[mTabTitle.length];
        for (int i = 0; i < mTabTitle.length; i++) {
            mLl_main_tab_content[i] = (LinearLayout) mLl_main_tab.getChildAt(i);
            final int currentItem = i;
            mLl_main_tab_content[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTabData();
                    mTab_text[currentItem].setTextColor(getChosenColor());
                    mTab_img[currentItem].setImageResource(img_chosen[currentItem]);
                    mLl_main_tab_content[currentItem].setClickable(false);
                    Toast.makeText(MainActivity.this, "点击的是:::" + currentItem, Toast.LENGTH_SHORT).show();
                    setChosenFragment(mFragments.get(currentItem));
                }
            });
            mTab_text[i] = (TextView) mLl_main_tab_content[i].getChildAt(1);
            mTab_img[i] = (ImageView) mLl_main_tab_content[i].getChildAt(0);
            mTab_text[i].setText(mTabTitle[i]);
        }
        resetTabData();
        mTab_text[0].setTextColor(getChosenColor());
        mTab_img[0].setImageResource(img_chosen[0]);
        mLl_main_tab_content[0].setClickable(false);
    }

    private void resetTabData() {
        for (int i = 0; i < mTabTitle.length; i++) {
            mTab_text[i].setTextColor(ContextCompat.getColor(this, R.color.colorTabText));
            mTab_img[i].setImageResource(img_unchosen[i]);
            mLl_main_tab_content[i].setClickable(true);
        }
    }

    public int getChosenColor() {
        return ContextCompat.getColor(this, R.color.colorTabChosen);
    }

    /**
     * 比较常用的配置方案
     *
     * @return
     */
    private ImageLoaderConfiguration getSimpleConfig() {
        //设置缓存的路径
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
//                .memoryCacheExtraOptions(480, 800) //即保存的每个缓存文件的最大长宽
                .threadPriority(Thread.NORM_PRIORITY - 2) //线程池中线程的个数
                .denyCacheImageMultipleSizesInMemory() //禁止缓存多张图片
                .memoryCache(new LRULimitedMemoryCache(20 * 1024 * 1024)) //缓存策略
                .memoryCacheSize(50 * 1024 * 1024) //设置内存缓存的大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //缓存文件名的保存方式
                .diskCacheSize(20 * 1024 * 1024) //磁盘缓存大小
                .tasksProcessingOrder(QueueProcessingType.LIFO) //工作队列
                .diskCacheFileCount(200) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir)) //自定义缓存路径
                //.writeDebugLogs() // Remove for release app
                .build();
        return config;
    }
}
