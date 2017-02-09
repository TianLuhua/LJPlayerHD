package com.example.lj.ljplayerhd.main.firstpager.expandable.adapter;

import android.view.View;

import com.example.lj.ljplayerhd.bean.PageBean;

/**
 * Created by wh on 2016/12/7.
 */

public abstract class MOnClickListener implements View.OnClickListener{

    protected PageBean.VideoContentBean bean;

    public MOnClickListener(PageBean.VideoContentBean bean){
        this.bean=bean;
    }
}
