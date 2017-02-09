package com.example.lj.ljplayerhd.main.personal;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.main.offline.dlna.DlnaFragment;
import com.example.lj.ljplayerhd.main.offline.download.DownLoadFragment;
import com.example.lj.ljplayerhd.main.offline.local.LocalFragment;
import com.example.lj.ljplayerhd.main.personal.adapter.RecyclerListMultiAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class PersonalFragment extends BaseFragment {

    AppBarLayout mAppBarLayout;
    Toolbar mToolbar;
    ViewGroup mTitleContainerLine;
    TextView mTvTitleToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_center_copy;
    }

    @Override
    public void initViews() {
        mAppBarLayout = (AppBarLayout) root.findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) root.findViewById(R.id.toolbar_start);
        mTitleContainerLine = (ViewGroup) root.findViewById(R.id.layout_start_title_root);
        mTvTitleToolbar = (TextView) root.findViewById(R.id.tv_start_toolbar_title);

        mAppBarLayout.addOnOffsetChangedListener(offsetChangedListener);
        initialize();

    }

    private void initialize() {
        String[] titles = getResources().getStringArray(R.array.fragment_personal_items);
        int[] iocs = getResources().getIntArray(R.array.fragment_personal_ioc);
        RecyclerListMultiAdapter listAdapter = new RecyclerListMultiAdapter(titles, iocs);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_start_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void initEvents() {

    }

    /**
     * AppBarLayout的offset监听。
     */
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            ViewCompat.setAlpha(mTitleContainerLine, 1 - percentage);
            ViewCompat.setAlpha(mTvTitleToolbar, percentage);
            mToolbar.getBackground().mutate().setAlpha((int) (255 * percentage));

//        if (percentage >= 1 || percentage <= 0) {
//            int distance = getResources().getDimensionPixelSize(android.support.design.);
//            mContentRoot.animate().translationY(distance * percentage).start();
//        }
        }
    };


}
