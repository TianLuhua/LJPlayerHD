package com.example.lj.ljplayerhd.main.search;

import android.content.Context;
import android.text.TextUtils;

import com.example.lj.ljplayerhd.bean.SearchBean;
import com.example.lj.ljplayerhd.media.details.VideoDetailActivity;
import com.example.lj.ljplayerhd.utils.ToastUtil;

/**
 * Created by wh on 2016/12/6.
 */

public class SearchResultItemClickHandler {

    private Context mContext;

    public SearchResultItemClickHandler(Context context) {
        this.mContext = context;
    }

    public void itemClick(SearchBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getName()))
            mContext.startActivity(VideoDetailActivity.newIntent(mContext, bean.getName()));
    }
}
