package com.example.lj.ljplayerhd.base;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.lj.ljplayerhd.R;

/**
 * Created by wh on 2016/11/19.
 */

public abstract class BaseExpandableFragment extends BaseFragment implements ExpandableListView.OnChildClickListener {

    private ExpandableListView mListView;
    private ProgressBar mProgress;
    public ExpandableListView getListView(){
        return mListView;
    }
    private boolean isVisible;
    private boolean isInit;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible=true;
            startLoadData();
        }else {
            isVisible=false;
        }
    }

    private void startLoadData(){
        if(isVisible && isInit){
            initData();
            isInit=false;
        }
    }

    protected void showProgress(){
        if(mProgress==null)
            return;
        mProgress.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    protected void stopProgress(){
        if(mProgress==null)
            return;
        mProgress.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.basefragment_expandable;
    }

    @Override
    public void initViews() {
        mProgress= (ProgressBar) root.findViewById(R.id.fragment_base_expandable_progress);
        mListView = (ExpandableListView) root.findViewById(R.id.fragment_base_expandable_list);
        init();
        isInit=true;
        startLoadData();
    }

    public void setAdapter(BaseExpandableListAdapter adapter) {
        if (adapter != null)
            mListView.setAdapter(adapter);
    }

    public void addHeaderView(View headView) {
        if (headView != null)
            mListView.addHeaderView(headView);
    }

    public void addFooterView(View footerView) {
        if (footerView != null)
            mListView.addFooterView(footerView);
    }

    @Override
    public void initEvents() {
        mListView.setOnChildClickListener(this);
    }

    private ExpandableListView.OnChildClickListener mChildListener;

    public void setOnChildClickListener(ExpandableListView.OnChildClickListener l){
        this.mChildListener=l;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if(mChildListener!=null)
            return  mChildListener.onChildClick(parent,v,groupPosition,childPosition,id);
        return false;
    }

    public abstract void init();

    public abstract void initData();
}
