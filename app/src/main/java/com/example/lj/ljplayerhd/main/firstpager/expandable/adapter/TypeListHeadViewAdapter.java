package com.example.lj.ljplayerhd.main.firstpager.expandable.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/11/27.
 */

public class TypeListHeadViewAdapter extends PagerAdapter {

    private List<PageBean.VideoContentBean> mData;
    private Context mContext;
    public static int maxCount = 4;

    public TypeListHeadViewAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<PageBean.VideoContentBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(mContext);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        initData(item, position);
        container.addView(item);
        return item;
    }

    private void initData(final ImageView item, int position) {
        PageBean.VideoContentBean bean = mData.get(position);
        if (bean == null)
            return;
        if (!TextUtils.isEmpty(bean.getPicaddr()))
            Glide.with(mContext)
                    .load(IDatas.WebService.THUMB_PATH+bean.getPicaddr())
                    .placeholder(R.drawable.film_cover_loading)
                    .error(R.drawable.film_cover_loading)
                    .into(item);
        item.setOnClickListener(new MOnClickListener(bean) {
            @Override
            public void onClick(View view) {
                if(itemClickListener!=null)
                    itemClickListener.onItemClick(this.bean);
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener l){
        itemClickListener=l;
    }
}
