package com.example.lj.ljplayerhd.media.details;

import android.util.Log;

import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.bean.SearchBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.GSONUtils;
import com.example.lj.ljplayerhd.utils.NetUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by wh on 2016/12/7.
 */

public class VideoDetailPresenter implements VideoDetailContract.Presenter {

    private VideoDetailContract.View mView;
    private static final int recommendCount = 9;

    public VideoDetailPresenter(VideoDetailContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadDetailsData(String name) {
        try {
            //因为拿不到详情数据,只能通过搜索获取
            String url = IDatas.WebService.SEARCH_VIDEO
                    + "&key=" + URLEncoder.encode(name, "UTF-8");
            Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
            RxNoHttp.request(mView.getLifecycleProvider(), request, new Action1<Response<String>>() {
                @Override
                public void call(Response<String> response) {
                    if (response.get() == null)
                        return;
                    List<SearchBean> datas = GSONUtils.parseJsonArrayString(response.get());
                    if (datas != null && datas.size() > 0)
                        mView.updateHeadData(datas.get(0));
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadRecommendData() {
        String url = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + "video" + "&order=" + "hits" + "&page=" + "1";
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), url, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                if (response.get() == null)
                    return;
                List<PageBean.VideoContentBean> list = response.get().getPagecontent();
                if (list == null)
                    return;
                List<PageBean.VideoContentBean> datas = new ArrayList<PageBean.VideoContentBean>();
                Collections.shuffle(datas);
                if (list.size() > recommendCount) {
                    for (int i = 0; i < recommendCount; i++) {
                        datas.add(list.get(i));
                    }
                } else {
                    datas = list;
                }
                mView.updateRecommendData(datas);
            }
        });
    }

    //下载操作,发送信息执行下载
    @Override
    public void download(SearchBean bean) {
        this.selectedBean = bean;
        List<DefinitionBean> data = NetUtil.getPlayUrl(bean.getAddress());
        mView.showDefinitionDialog(data, IDatas.Messages.REQUEST_DOWNLOAD);
    }

    @Override
    public void comment(SearchBean bean) {
        if (bean != null) {
            mView.goCommentActivity(bean.getMark(), bean.getId(), bean.getName());
        }
    }

    @Override
    public void back() {
        mView.back();
    }

    @Override
    public void play(SearchBean bean) {
        List<DefinitionBean> data = NetUtil.getPlayUrl(bean.getAddress());
        mView.showDefinitionDialog(data, IDatas.Messages.REQUEST_PLAY);
    }

    private SearchBean selectedBean;

    @Override
    public void download(String url) {
        if (selectedBean != null)
            ToastUtil.showShort("下载:[" + selectedBean.getName() + ":" + url + "]");
        startDownLoad(selectedBean, url);
    }

    //通过RxBus将对象发送到Service下载
    private void startDownLoad(SearchBean selectedBean, String url) {
        DownLoadTask task = new DownLoadTask();
        task.setId(Integer.valueOf(selectedBean.getId()));
        task.setAddress(url);
        task.setName(selectedBean.getName());
        task.setPicaddr(selectedBean.getPicaddr());
        task.setTineLen(selectedBean.getTimelen());
        RxBus.getInstance().send(task);
    }

    @Override
    public void onItemClick(PageBean.VideoContentBean bean) {
        mView.goVideoDetailsActivity(bean.getName());
    }
}
