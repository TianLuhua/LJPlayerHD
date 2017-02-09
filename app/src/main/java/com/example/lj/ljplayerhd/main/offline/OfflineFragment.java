package com.example.lj.ljplayerhd.main.offline;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.lj.ljplayerhd.adapter.LJFragmentPagerAdapter;
import com.example.lj.ljplayerhd.base.BasePagerFragment;
import com.example.lj.ljplayerhd.main.offline.dlna.DlnaFragment;
import com.example.lj.ljplayerhd.main.offline.download.DownLoadFragment;
import com.example.lj.ljplayerhd.main.offline.local.LocalFragment;

import java.util.ArrayList;

/**
 * Created by wh on 2016/11/19.
 */

public class OfflineFragment extends BasePagerFragment {

    private final int DLNAFRAGMENT = 0;
    private final int DOWNLOADFRAGMENT = 1;
    private final int LOCALFRAGMENT = 2;

    private ArrayList<Fragment> mFagments = new ArrayList<Fragment>();
    private String[] mTitles = {"DLAN", "离线下载", "本地"};
    private LJFragmentPagerAdapter mAdapter;

    @Override
    public void init() {
        for (int i = 0; i < mTitles.length; i++) {
            switch (i) {
                case DLNAFRAGMENT:
                    mFagments.add(new DlnaFragment());
                    break;
                case DOWNLOADFRAGMENT:
                    mFagments.add(new DownLoadFragment());
                    break;
                case LOCALFRAGMENT:
                    mFagments.add(new LocalFragment());
                    break;
                default:
            }
        }

        mAdapter = new LJFragmentPagerAdapter(getChildFragmentManager());
        setAdapter(mAdapter);
        mAdapter.setFragments(mFagments);
        setTitles(mTitles);
    }
}
