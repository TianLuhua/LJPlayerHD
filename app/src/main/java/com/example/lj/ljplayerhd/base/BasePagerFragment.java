package com.example.lj.ljplayerhd.base;

import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.widget.View_CircleDotViewPager;
import com.flyco.tablayout.SlidingTabLayout;


/**
 * Created by wh on 2016/11/19.
 */

public abstract class BasePagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;


    protected void setTitles(String[] mTitles) {
        if (mTitles != null && mTitles.length > 0) {
            mSlidingTabLayout.setVisibility(View.VISIBLE);
            this.mSlidingTabLayout.setViewPager(mViewPager, mTitles);
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public SlidingTabLayout getSlidingTabLayout() {
        return mSlidingTabLayout;
    }

    @Override
    public int getLayoutId() {
        return R.layout.basefragment_viewpager;
    }

    @Override
    public void initViews() {
        mViewPager = (ViewPager) root.findViewById(R.id.fragment_base_viewpager_pager);
        mSlidingTabLayout = (SlidingTabLayout) root.findViewById(R.id.tablayout);
        init();

    }

    @Override
    public void initEvents() {
        mViewPager.addOnPageChangeListener(this);
    }

    public void setAdapter(PagerAdapter adapter) {
        if (mViewPager != null)
            mViewPager.setAdapter(adapter);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener;

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener l) {
        this.mPageChangeListener = l;
    }

    public abstract void init();

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPageChangeListener != null)
            mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (mPageChangeListener != null)
            mPageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPageChangeListener != null)
            mPageChangeListener.onPageScrollStateChanged(state);
    }
}
