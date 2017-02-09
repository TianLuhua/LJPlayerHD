package com.example.lj.ljplayerhd.main.firstpager;

import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.bean.ConfigBean;
import com.example.lj.ljplayerhd.main.firstpager.expandable.TypeExpandableFragment;
import com.example.lj.ljplayerhd.main.firstpager.live.LiveFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/11/26.
 */

public class FirstPagerPresenter implements FirstPagerContract.Presenter {

    private FirstPagerContract.View mView;

    public FirstPagerPresenter(FirstPagerContract.View view) {
        this.mView = view;
    }

    @Override
    public void setUpFragment() {
            ConfigBean config = LJApplication.getConfig();
            List<ConfigBean.TypeBean> types = config.getType();
            if (types != null) {
                //直播不属于该服务器,手动加载
                String[] mTitles = new String[types.size()+1];
                List<Fragment> fragments = new ArrayList<Fragment>();
                for (int i = 0; i < mTitles.length-1; i++) {
                    if (types.get(i) != null) {
                        mTitles[i] = types.get(i).getName();
                        fragments.add(TypeExpandableFragment.newInstance(types.get(i)));
                    }
                }
                fragments.add(LiveFragment.newInstance());
                mTitles[types.size()]="直播";
                mView.setFragments(fragments);
                mView.setTabTitles(mTitles);
            }
    }
}
