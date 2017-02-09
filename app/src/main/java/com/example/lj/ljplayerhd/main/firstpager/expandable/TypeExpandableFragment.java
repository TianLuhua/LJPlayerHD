package com.example.lj.ljplayerhd.main.firstpager.expandable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.lj.ljplayerhd.base.BaseExpandableFragment;
import com.example.lj.ljplayerhd.bean.ConfigBean;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.firstpager.expandable.adapter.OnItemClickListener;
import com.example.lj.ljplayerhd.main.firstpager.expandable.adapter.TypeExpandableAdapter;
import com.example.lj.ljplayerhd.main.firstpager.expandable.adapter.TypeListHeadViewAdapter;
import com.example.lj.ljplayerhd.media.category.CategoryActivity;
import com.example.lj.ljplayerhd.media.category.CategoryFragment;
import com.example.lj.ljplayerhd.media.definition.DefinitionChoiceDialog;
import com.example.lj.ljplayerhd.media.details.VideoDetailActivity;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.NetUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.example.lj.ljplayerhd.widget.View_ListHeadView;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by wh on 2016/11/21.
 */

public class TypeExpandableFragment extends BaseExpandableFragment implements TypePagerContract.View {

    private TypeExpandableAdapter mAdapter;
    private TypeListHeadViewAdapter mHeadAdapter;
    private String headUrl = "";
    private String timeUrl = "";
    private String hitsUrl = "";
    private String ratingUrl = "";
    private String effectUrl = "";
    private TypePagerContract.Presenter mPresenter;
    private View_ListHeadView mHeadView;
    private String mark;
    private String name;

    public TypeExpandableFragment() {
    }

    public static TypeExpandableFragment newInstance(ConfigBean.TypeBean type) {
        Bundle argument = new Bundle();
        argument.putString(IDatas.JsonKey.MARK, type.getMark());
        argument.putString(IDatas.JsonKey.NAME,type.getName());
        TypeExpandableFragment fragment = new TypeExpandableFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void init() {
        Bundle arguments = getArguments();
        mark = arguments.getString(IDatas.JsonKey.MARK);
        name = arguments.getString(IDatas.JsonKey.NAME);
        if (mark != null) {
            headUrl = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + mark + "&order=" + "hits" + "&page=" + "2";
            timeUrl = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + mark + "&order=" + "time" + "&page=" + "1";
            hitsUrl = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + mark + "&order=" + "hits" + "&page=" + "2";
            ratingUrl = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + mark + "&order=" + "rating" + "&page=" + "3";
            effectUrl = IDatas.WebService.VIDEO_LIST_PATH + "&mark=" + mark + "&order=" + "effect" + "&page=" + "4";
        }
        mAdapter = new TypeExpandableAdapter(getListView());
        setAdapter(mAdapter);
        mHeadView = new View_ListHeadView(getActivity());
        addHeaderView(mHeadView);
        mHeadAdapter = new TypeListHeadViewAdapter(getActivity());
        mHeadView.setAdapter(mHeadAdapter);
        mPresenter = new TypePagerPresenter(this);
        mHeadAdapter.setOnItemClickListener(itemClickListener);
        mAdapter.setOnItemClickListener(itemClickListener);
        getListView().setOnGroupClickListener(groupClickListener);
    }

    @Override
    public void initData() {
        if (mAdapter != null && mAdapter.getGroupCount() > 0)
            return;
        showProgress();
        RxNoHttp.requestJavaBean(this, headUrl, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                mPresenter.updateHeadData(response.get());
            }
        });
        RxNoHttp.requestJavaBean(this, timeUrl, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                mPresenter.updateData(IDatas.JsonKey.ORDER_TIME, response.get());
            }
        });
        RxNoHttp.requestJavaBean(this, hitsUrl, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                mPresenter.updateData(IDatas.JsonKey.ORDER_HITS, response.get());
            }
        });
        RxNoHttp.requestJavaBean(this, ratingUrl, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                mPresenter.updateData(IDatas.JsonKey.ORDER_RATING, response.get());
            }
        });
        RxNoHttp.requestJavaBean(this, effectUrl, new Action1<Response<PageBean>>() {
            @Override
            public void call(Response<PageBean> response) {
                mPresenter.updateData(IDatas.JsonKey.ORDER_EFFECT, response.get());
            }
        });
    }


    @Override
    public void updateData(Map<String, List<PageBean.VideoContentBean>> data) {
        stopProgress();
        if (mAdapter != null)
            mAdapter.addData(data);
    }

    @Override
    public void updateHeadView(List<PageBean.VideoContentBean> contents) {
        if (mHeadAdapter != null)
            mHeadAdapter.setData(contents);
    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(PageBean.VideoContentBean bean) {
            if (bean == null || TextUtils.isEmpty(bean.getName()))
                return;
            if ("video".equals(mark))
                getActivity().startActivity(VideoDetailActivity.newIntent(getActivity(), bean.getName()));
            else{
                FragmentManager manager = getFragmentManager();
                List<DefinitionBean> data = NetUtil.getPlayUrl(bean.getAddress());
                DefinitionChoiceDialog dialog = DefinitionChoiceDialog
                        .newInstance((ArrayList<DefinitionBean>) data);
                dialog.setTargetFragment(TypeExpandableFragment.this, IDatas.Messages.REQUEST_PLAY);
                dialog.show(manager, IDatas.JsonKey.DEFINITION);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode==IDatas.Messages.REQUEST_PLAY){
            String url=data.getStringExtra(IDatas.JsonKey.ADDRESS);
            getActivity().startActivity(FullScreenPlayActivity.newIntent(getActivity(), url));
        }
    }

    private ExpandableListView.OnGroupClickListener groupClickListener =new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
            getActivity().startActivity(CategoryActivity.newIntent(getActivity(),mark,name));
            return true;
        }
    };
}
