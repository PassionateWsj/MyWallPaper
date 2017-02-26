package com.jameswong.mywallpaper.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.fragment.WallPaperGridViewFragment;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 17:17
 * name: 第一个界面的可复用的Activity
 * desc:
 * step:
 * *************************************************************
 */

public class ContentActivity extends FragmentActivity {

    private WallPaperGridViewFragment mFragment;
    private String mPicCategoryName;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mPicCategoryName = getIntent().getStringExtra("PicCategoryName");
        mId = getIntent().getStringExtra("ID");
        Toast.makeText(this, "拿到的PicCategoryName:::" + mPicCategoryName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "拿到的ID:::" + mId, Toast.LENGTH_SHORT).show();
        if (mId != null) {
            mFragment = WallPaperGridViewFragment.getInstance(mPicCategoryName, mId);
        } else {
            mFragment = WallPaperGridViewFragment.getInstance(mPicCategoryName);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_container, mFragment).commit();
    }
}
