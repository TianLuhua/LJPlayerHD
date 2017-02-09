package com.example.lj.ljplayerhd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trello.rxlifecycle.components.support.RxDialogFragment;

/**
 * Created by wh on 2016/11/19.
 */

public abstract class BaseDialogFragment extends RxDialogFragment {
    protected View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recoverState();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (root == null)
            root = inflater.inflate(getLayoutId(), container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initEvents();
    }

    @Override
    public void onPause() {
        saveState();
        super.onPause();
    }

    protected void saveState(){

    }

    protected void recoverState(){

    }

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initEvents();
}
