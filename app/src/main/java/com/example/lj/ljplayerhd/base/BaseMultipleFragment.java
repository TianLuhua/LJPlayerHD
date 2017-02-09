package com.example.lj.ljplayerhd.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.lj.ljplayerhd.R;

import java.util.List;

/**
 * Created by wh on 2016/11/27.
 */

public abstract class BaseMultipleFragment extends BaseFragment {


    private List<Fragment> fragments;

    protected abstract int getFragmentId();

    public abstract List<Fragment> createFragments();

    public abstract void initView();

    @Override
    public void initViews() {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(getFragmentId());
        if (fragment == null) {
            fragments = createFragments();
            FragmentTransaction begin = fm.beginTransaction();
            if (fragments != null) {
                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i) != null)
                        begin.add(getFragmentId(), fragments.get(i)).hide(fragments.get(i));
                }
                begin.commit();
                showFragment(fragments.get(0), false);
            }
        }
        initView();
    }

    private Fragment mCurrentFragment;

    public void hideFragment() {
        if (!checkedFragment(mCurrentFragment))
            return;
        FragmentTransaction begin = getFragmentManager().beginTransaction();
        begin.hide(mCurrentFragment).commit();
        mCurrentFragment = null;
    }

    public void showFragment(Fragment fragment, boolean isAnim) {
        if (!checkedFragment(fragment))
            return;
        if (mCurrentFragment == fragment)
            return;
        FragmentTransaction begin = getFragmentManager().beginTransaction();
        if (isAnim)
            begin.setCustomAnimations(R.anim.fragment_video_controller_show, 0);
        if (mCurrentFragment != null)
            begin.hide(mCurrentFragment);
        begin.show(fragment).commit();
        mCurrentFragment = fragment;
    }

    public boolean isShowFragment(Fragment fragment) {
        return mCurrentFragment != null && mCurrentFragment == fragment;
    }

    private boolean checkedFragment(Fragment fragment) {
        if (!fragments.contains(fragment))
            return false;
        if (fragment == null)
            return false;
        return true;
    }
}
