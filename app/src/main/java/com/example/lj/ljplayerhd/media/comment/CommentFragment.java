package com.example.lj.ljplayerhd.media.comment;

import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.CommentBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.FragmentCommentBinding;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.example.lj.ljplayerhd.widget.SwipeToLoadRecyclerView;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.List;

/**
 * Created by wh on 2016/12/28.
 */

public class CommentFragment extends BaseFragment implements SwipeToLoadRecyclerView.OnLoadMoreListener
        ,CommentContract.View{

    private CommentContract.Presenter mPresenter;
    private FragmentCommentBinding commentBinding;
    private CommentAdapter mAdapter;
    private String mark;
    private String vId;

    private CommentFragment() {
    }

    ;

    public static CommentFragment newInstance(String mark, String vId,String name) {
        Bundle args = new Bundle();
        args.putString(IDatas.JsonKey.MARK, mark);
        args.putString(IDatas.JsonKey.ID, vId);
        args.putString(IDatas.JsonKey.NAME,name);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initViews() {
        commentBinding = FragmentCommentBinding.bind(root);
        mAdapter = new CommentAdapter(getActivity());
        commentBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentBinding.swipeTarget.setAdapter(mAdapter);
    }

    @Override
    public void initEvents() {
        String name=getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.NAME) == null ? "" : getArguments().getString(IDatas.JsonKey.NAME);
        commentBinding.title.setText(name);
        commentBinding.setPresenter((CommentPresenter) mPresenter);
        this.mark = getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.MARK) == null ? "" : getArguments().getString(IDatas.JsonKey.MARK);
        this.vId = getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.ID) == null ? "" : getArguments().getString(IDatas.JsonKey.ID);
        if (!TextUtils.isEmpty(mark) && !TextUtils.isEmpty(vId))
            mPresenter.loadCommentData(mark, vId);

        commentBinding.swipeTarget.setSwipeToLoadLayout(commentBinding.loadLayout);
        commentBinding.swipeTarget.setOnLoadMoreListener(this);
    }

    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public FragmentLifecycleProvider getLifecycleProvider() {
        return this;
    }

    @Override
    public void updateComment(List<CommentBean.PagecontentBean> data) {
        if (data != null)
            mAdapter.setData(data);
    }

    @Override
    public void back() {
        getActivity().finish();
    }

    @Override
    public void setOver(boolean b) {
        commentBinding.swipeTarget.setOver(b);
    }

    @Override
    public void onLoadFinish() {
        commentBinding.swipeTarget.onLoadFinish();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadNextPage();
    }

    @Override
    public void onLoadOver() {
        ToastUtil.showShort("已经到底啦!");
    }
}
