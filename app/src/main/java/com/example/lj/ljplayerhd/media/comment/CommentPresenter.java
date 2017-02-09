package com.example.lj.ljplayerhd.media.comment;

import com.example.lj.ljplayerhd.bean.CommentBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wh on 2016/12/28.
 */

public class CommentPresenter implements CommentContract.Presenter {

    private CommentContract.View mView;
    private List<CommentBean.PagecontentBean> pageData;
    private int total;
    private int pageSize;
    private int page =1;
    private String dataUrl="";

    public CommentPresenter(CommentContract.View view){
        this.mView=view;
        pageData=new ArrayList<>();
        view.setPresenter(this);
    }

    @Override
    public void loadCommentData(String mark,String vId) {
        setDataUrl(mark,vId);
        loadData();
    }

    private void setDataUrl(String mark,String vId){
        dataUrl=IDatas.WebService.VIDEO_COMMENTLIST_PATH + "&mark=" + mark
                + "&mid=" + vId;
    }

    private String getDataUrl() {
        return dataUrl+"@page="+page;
    }

    private void loadData() {
        this.page = 1;
        mView.setOver(false);
        if (pageData != null) {
            pageData.clear();
            mView.updateComment(pageData);
        }
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), getDataUrl(), new Action1<Response<CommentBean>>() {
            @Override
            public void call(Response<CommentBean> response) {
                if(response.get()==null || response.get().getPagecontent() ==null)
                    return;
                flushData(response.get());
            }
        });
    }

    private void flushData(CommentBean response) {
        pageSize = Integer.parseInt(response.getSplitpage().getPagesize());
        total = Integer.parseInt(response.getSplitpage().getTotal());
        pageData.addAll(response.getPagecontent());
        mView.updateComment(pageData);
        page++;
    }

    @Override
    public void back() {
        mView.back();
    }

    @Override
    public void loadNextPage() {
        if (pageData.size() >= total || page > pageSize) {
            mView.setOver(true);
            return;
        }
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), getDataUrl(), new Action1<Response<CommentBean>>() {
            @Override
            public void call(Response<CommentBean> response) {
                if(response.get()==null || response.get().getPagecontent() ==null)
                    return;
                page++;
                pageData.addAll(response.get().getPagecontent());
                mView.updateComment(pageData);
                mView.onLoadFinish();
            }
        });
    }
}
