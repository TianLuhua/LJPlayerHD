package com.example.lj.ljplayerhd.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.utils.DensityUtil;

/**
 * Created by wh on 2016/11/27.
 */

public class View_ListHeadView extends FrameLayout implements ViewPager.OnPageChangeListener{

    private View_CircleDotViewPager mPager;

    public View_ListHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public View_ListHeadView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mPager = (View_CircleDotViewPager) LayoutInflater.from(context).inflate(R.layout.video_list_head_view, null);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(context,180));
        addView(mPager,params);
        mPager.addOnPageChangeListener(this);
    }

    public void setAdapter(PagerAdapter adapter) {
        if (adapter != null && mPager != null)
            mPager.setAdapter(adapter);
    }

    private ViewPager.OnPageChangeListener mPagerListener;

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener l){
        mPagerListener=l;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPagerListener!=null)
            mPagerListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if(mPagerListener!=null)
            mPagerListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(mPagerListener!=null)
            mPagerListener.onPageScrollStateChanged(state);
    }
}
