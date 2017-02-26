package com.jameswong.mywallpaper.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.activity.CollectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 13:26
 * name: 更多界面的Fragment
 * desc:
 * step:
 * *************************************************************
 */

public class MoreFragment extends Fragment {
    private static final String TAG = "hjjzz";

    @BindView(R.id.btn_my_collect)
    Button mBtnMyCollect;
    @BindView(R.id.btn_my_download)
    Button mBtnMyDownload;
    @BindView(R.id.btn_recommend_to_friend)
    Button mBtnRecommendToFriend;
    @BindView(R.id.btn_more_give_good_reputation)
    Button mBtnMoreGiveGoodReputation;
    @BindView(R.id.btn_more_setting)
    Button mBtnMoreSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setListener();
    }



    private void setListener() {
        mBtnMyCollect.setOnClickListener(fragment_more_item_click);
        mBtnMyDownload.setOnClickListener(fragment_more_item_click);
        mBtnRecommendToFriend.setOnClickListener(fragment_more_item_click);
        mBtnMoreGiveGoodReputation.setOnClickListener(fragment_more_item_click);
        mBtnMoreSetting.setOnClickListener(fragment_more_item_click);
    }

    View.OnClickListener fragment_more_item_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_my_collect:
                    Intent intent = new Intent(getActivity(), CollectActivity.class);
                    startActivity(intent);

                    Log.i(TAG, "MoreFragment:::" + "我的收藏");
                    break;
                case R.id.btn_my_download:
                    Log.i(TAG, "MoreFragment:::" + "我的下载");
                    break;
                case R.id.btn_recommend_to_friend:
                    Log.i(TAG, "MoreFragment:::" + "推荐给好友");
                    break;
                case R.id.btn_more_give_good_reputation:
                    Log.i(TAG, "MoreFragment:::" + "给个好评");
                    break;
                case R.id.btn_more_setting:
                    Log.i(TAG, "MoreFragment:::" + "设置");
                    break;
            }
        }
    };

}
