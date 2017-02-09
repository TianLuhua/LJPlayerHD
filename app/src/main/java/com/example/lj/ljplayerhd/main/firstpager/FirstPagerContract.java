package com.example.lj.ljplayerhd.main.firstpager;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by wh on 2016/11/26.
 */

public class FirstPagerContract {
    public interface View {

        void setTabTitles(String[] mTitles);

        void setFragments(List<Fragment> fragments);
    }

    public interface Presenter {
        void setUpFragment();
    }
}
