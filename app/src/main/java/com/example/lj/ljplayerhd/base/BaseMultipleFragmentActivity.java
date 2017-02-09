package com.example.lj.ljplayerhd.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

public abstract class BaseMultipleFragmentActivity extends RxFragmentActivity {


    private List<Fragment> fragments;

    protected int getLayoutId() {
        return 0;
    }

    protected int getFragmentId() {
        return 0;
    }

    public abstract List<Fragment> createFragments();

    protected void initViews() {

    }

    protected void initEvents() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews();
        initEvents();
        FragmentManager fm = getSupportFragmentManager();
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
                showFragment(fragments.get(0));
            }
        }
    }

    private Fragment mCurrentFragment;

    public void showFragment(Fragment fragment) {
        if (!fragments.contains(fragment)) return;
        if (fragment == null)
            return;
        if (mCurrentFragment == fragment)
            return;
        FragmentTransaction begin = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null)
            begin.hide(mCurrentFragment);
        begin.show(fragment).commit();
        mCurrentFragment = fragment;
    }
}
