package com.example.lj.ljplayerhd.media.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.adapter.VideoItemRecyclerAdapter;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.bean.SearchBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.FragmentVideoDetailsBinding;
import com.example.lj.ljplayerhd.databinding.VideoDetailsViewBinding;
import com.example.lj.ljplayerhd.main.search.SearchActivity;
import com.example.lj.ljplayerhd.media.category.CategoryActivity;
import com.example.lj.ljplayerhd.media.comment.CommentActivity;
import com.example.lj.ljplayerhd.media.definition.DefinitionChoiceDialog;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.example.lj.ljplayerhd.utils.NetUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.example.lj.ljplayerhd.widget.FullyGridLayoutManager;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/11/19.
 */

public class VideoDetailsFragment extends BaseFragment implements VideoDetailContract.View {

    private VideoDetailContract.Presenter mPresenter;
    private VideoDetailsViewBinding headDetailsViewBinding;
    private FragmentVideoDetailsBinding fragmentVideoDetailsBinding;
    private VideoItemRecyclerAdapter mAdapter;

    public static VideoDetailsFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString(IDatas.JsonKey.NAME, name);
        VideoDetailsFragment fragment = new VideoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public VideoDetailsFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_details;
    }

    @Override
    public void initViews() {
        fragmentVideoDetailsBinding = FragmentVideoDetailsBinding.bind(root);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.video_details_view, null);
        headDetailsViewBinding = VideoDetailsViewBinding.bind(view);
        fragmentVideoDetailsBinding.detailsContain.addView(view);
        fragmentVideoDetailsBinding.recycler.setLayoutManager(new FullyGridLayoutManager(getActivity(), 3));
        mAdapter = new VideoItemRecyclerAdapter(mPresenter);
        fragmentVideoDetailsBinding.recycler.setAdapter(mAdapter);
    }

    @Override
    public void initEvents() {
        mPresenter.loadDetailsData(getArguments().getString(IDatas.JsonKey.NAME));
        mPresenter.loadRecommendData();
        fragmentVideoDetailsBinding.setPresenter((VideoDetailPresenter) mPresenter);
    }

    @Override
    public void setPresenter(VideoDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public FragmentLifecycleProvider getLifecycleProvider() {
        return this;
    }

    @Override
    public void updateHeadData(SearchBean bean) {
        headDetailsViewBinding.setBean(bean);
        if (!TextUtils.isEmpty(bean.getPicaddr()))
            Glide.with(getActivity())
                    .load(IDatas.WebService.THUMB_PATH + bean.getPicaddr())
                    .placeholder(R.drawable.film_cover_loading)
                    .error(R.drawable.film_cover_loading)
                    .into(headDetailsViewBinding.img);

        setDetail(bean);
    }

    @Override
    public void updateRecommendData(List<PageBean.VideoContentBean> datas) {
        mAdapter.setData(datas);
    }

    @Override
    public void back() {
        getActivity().finish();
    }

    @Override
    public void goCommentActivity(String mark, String id, String name) {
        startActivity(CommentActivity.newIntent(getActivity(), mark, id, name));
    }

    @Override
    public void showDefinitionDialog(List<DefinitionBean> data,int requestCode) {
        FragmentManager manager = getFragmentManager();
        DefinitionChoiceDialog dialog = DefinitionChoiceDialog
                .newInstance((ArrayList<DefinitionBean>) data);
        dialog.setTargetFragment(VideoDetailsFragment.this, requestCode);
        dialog.show(manager, IDatas.JsonKey.DEFINITION);
    }

    @Override
    public void goVideoDetailsActivity(String name) {
        getActivity().startActivity(VideoDetailActivity.newIntent(getActivity(),name));
        getActivity().finish();
    }

    public void setDetail(SearchBean detail) {
        fragmentVideoDetailsBinding.setDetailBean(detail);
        fragmentVideoDetailsBinding.executePendingBindings();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == IDatas.Messages.REQUEST_DOWNLOAD) {
            String url=data.getStringExtra(IDatas.JsonKey.ADDRESS);
            mPresenter.download(url);
        }
        if (requestCode==IDatas.Messages.REQUEST_PLAY){
            String url=data.getStringExtra(IDatas.JsonKey.ADDRESS);
            getActivity().startActivity(FullScreenPlayActivity.newIntent(getActivity(), url));
        }
    }
}
