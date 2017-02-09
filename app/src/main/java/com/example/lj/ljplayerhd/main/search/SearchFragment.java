package com.example.lj.ljplayerhd.main.search;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseMultipleFragment;
import com.example.lj.ljplayerhd.widget.View_SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/11/19.
 */

public class SearchFragment extends BaseMultipleFragment{

    private DefaultFragment defaultFragment;
    private ResultFragment resultFragment;
    private View cancel;
    private View_SearchView searchView;
    private int cancelVisible=View.GONE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected int getFragmentId() {
        return R.id.fragment_search_contain;
    }

    @Override
    public List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        defaultFragment = new DefaultFragment();
        resultFragment = new ResultFragment();
        fragments.add(defaultFragment);
        fragments.add(resultFragment);
        return fragments;
    }

    public void setCancelVisibility(int visibility){
        this.cancelVisible=visibility;
        if(cancel!=null)
            cancel.setVisibility(visibility);
    }

    @Override
    public void initView() {
        cancel = root.findViewById(R.id.fragment_search_cancel);
        cancel.setVisibility(cancelVisible);
        searchView = (View_SearchView) root.findViewById(R.id.fragment_search_searchview);
        searchView.setFocusable(false);
        searchView.setHintIconImageResource(R.drawable.icon_search);
        searchView.setCloseButtonImageResource(R.drawable.icon_search_close);
        searchView.setPlateBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden)
            if(searchView!=null)
                searchView.clearFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(searchView!=null)
            searchView.clearFocus();
    }

    @Override
    public void initEvents() {
        cancel.setOnClickListener(cancelClickListener);
        searchView.setOnQueryTextListener(onQueryTextListener);
    }

    private View.OnClickListener cancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    private View_SearchView.OnQueryTextListener onQueryTextListener = new View_SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            querySubmit(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(TextUtils.isEmpty(newText)){
                showFragment(defaultFragment,false);
            }
            return false;
        }
    };

    private void querySubmit(String query) {
        if(searchView!=null)
            searchView.clearFocus();
        showFragment(resultFragment,false);
        resultFragment.search(query);
    }
}
