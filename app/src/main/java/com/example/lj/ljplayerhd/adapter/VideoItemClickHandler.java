package com.example.lj.ljplayerhd.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.media.details.VideoDetailActivity;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.example.lj.ljplayerhd.utils.NetUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;

/**
 * Created by wh on 2016/12/13.
 */

public class VideoItemClickHandler {
    private VideoItemClickListener mListener;

    public VideoItemClickHandler(VideoItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void itemClick(PageBean.VideoContentBean bean) {
        if(mListener!=null)
            mListener.onItemClick(bean);
//        if (bean != null && !TextUtils.isEmpty(bean.getName())) {
//            if (mContext instanceof VideoDetailActivity) {
//                VideoDetailActivity activity = (VideoDetailActivity) mContext;
//                activity.finish();
//            }
//            if (!TextUtils.isEmpty(mark) && !"video".equals(mark)){
//                String url= NetUtil.getPlayUrl(bean.getAddress()).get(0).getUrl();
//                mContext.startActivity(FullScreenPlayActivity.newIntent(mContext,url));
//            }else
//                mContext.startActivity(VideoDetailActivity.newIntent(mContext, bean.getName()));
//        }
    }
}
