package com.example.lj.ljplayerhd.main.firstpager;

import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.adapter.LJFragmentPagerAdapter;
import com.example.lj.ljplayerhd.base.BasePagerFragment;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.rxjava.Message;
import com.example.lj.ljplayerhd.rxjava.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wh on 2016/11/21.
 */

public class FirstPagerFragment extends BasePagerFragment implements FirstPagerContract.View{

    private LJFragmentPagerAdapter mAdapter;
    private List<Fragment> fragments;
    private FirstPagerContract.Presenter mPresenter;

    @Override
    public void init() {
        mPresenter=new FirstPagerPresenter(this);
        fragments = new ArrayList<Fragment>();
        mAdapter = new LJFragmentPagerAdapter(getFragmentManager());
        setAdapter(mAdapter);
        if(LJApplication.getConfig()!=null){
            mPresenter.setUpFragment();
        }
        //防止网络错误没有获取到
        RxBus.getInstance().with(this).what(IDatas.Messages.CONFIG_INIT).receive(new Action1<Object>() {
            @Override
            public void call(Object message) {
                mPresenter.setUpFragment();
            }
        },null);
    }

    @Override
    public void setTabTitles(String[] mTitles) {
        setTitles(mTitles);
    }

    @Override
    public void setFragments(List<Fragment> fragments) {
        mAdapter.setFragments(fragments);
    }
}
