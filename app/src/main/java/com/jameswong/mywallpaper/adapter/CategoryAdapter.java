package com.jameswong.mywallpaper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jameswong.mywallpaper.R;
import com.jameswong.mywallpaper.bean.Category;
import com.jameswong.mywallpaper.view.SimpleProgressImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************************
 * author: JamesWong
 * time: 2016/10/08 16:03
 * name: 分类界面ListView的适配器
 * desc:
 * step:
 * *************************************************************
 */

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Category.DataBean> mData;
    private DisplayImageOptions mOptions;

    public CategoryAdapter(Context context) {
        this.context = context;
        //设置图片显示的样式
        mOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565) //图片质量
                .cacheOnDisk(true)  //是否缓存到sd卡
                .cacheInMemory(true) //缓存到内存
                .displayer(new RoundedBitmapDisplayer(15))
                .build();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Category.DataBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category_list, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mTv_category_pic_category_name.setText(getItem(position).getPicCategoryName());
        mViewHolder.mTv_category_desc_words.setText(getItem(position).getDescWords());

        mViewHolder.mSimpleProgressImageView.setImageUrl(getItem(position).getCategoryPic(),mOptions);
        return convertView;
    }

    class ViewHolder {
        private SimpleProgressImageView mSimpleProgressImageView;
        private TextView mTv_category_pic_category_name;
        private TextView mTv_category_desc_words;

        public ViewHolder(View itemView) {
            mSimpleProgressImageView = (SimpleProgressImageView) itemView.findViewById(R.id.iv_category_pic);
            mTv_category_pic_category_name = (TextView) itemView.findViewById(R.id.tv_category_pic_category_name);
            mTv_category_desc_words = (TextView) itemView.findViewById(R.id.tv_category_desc_words);
        }
    }

    /**
     * 设置数据
     * 1.刷新操作：先清空数据，然后加载最新的数据
     * 2.加载操作：直接加载到数据的最后面
     *
     * @param data
     */
    public void setData(List<Category.DataBean> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        notifyDataSetChanged();//刷新数据
    }

}
