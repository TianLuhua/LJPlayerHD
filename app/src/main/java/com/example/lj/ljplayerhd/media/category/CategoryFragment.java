package com.example.lj.ljplayerhd.media.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.adapter.VideoItemRecyclerAdapter;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.base.BaseMultipleFragment;
import com.example.lj.ljplayerhd.bean.CategoryModel;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.bean.PageBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.FragmentCategoryBinding;
import com.example.lj.ljplayerhd.main.search.SearchActivity;
import com.example.lj.ljplayerhd.media.definition.DefinitionChoiceDialog;
import com.example.lj.ljplayerhd.media.details.VideoDetailActivity;
import com.example.lj.ljplayerhd.media.details.VideoDetailsFragment;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.example.lj.ljplayerhd.widget.SwipeToLoadRecyclerView;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/11/19.
 */

public class CategoryFragment extends BaseMultipleFragment implements CategoryContract.View,
        SwipeToLoadRecyclerView.OnLoadMoreListener{

    Animation hideAnimation;
    FragmentCategoryBinding fragmentCategoryBinding;
    private CategoryPresenter mPresenter;
    VideoItemRecyclerAdapter mAdapter;
    private OrderMenuFragment orderMenuFragment;
    private CategoryMenuFragment categoryMenuFragment;

    public static CategoryFragment newInstance(String mark,String name) {
        Bundle args = new Bundle();
        args.putString(IDatas.JsonKey.MARK, mark);
        args.putString(IDatas.JsonKey.NAME,name);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected int getFragmentId() {
        return R.id.menu_contain;
    }

    @Override
    public List<Fragment> createFragments() {
        List<Fragment> list = new ArrayList<>();
        orderMenuFragment = new OrderMenuFragment(mPresenter);
        String mark = getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.MARK) == null ? "" : getArguments().getString(IDatas.JsonKey.MARK);
        categoryMenuFragment =CategoryMenuFragment.newInstance(mPresenter,mark);
        list.add(orderMenuFragment);
        list.add(categoryMenuFragment);
        return list;
    }

    @Override
    public void initView() {
        hideFragment();
        fragmentCategoryBinding = FragmentCategoryBinding.bind(root);
        mAdapter = new VideoItemRecyclerAdapter(mPresenter);
        String mark = getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.MARK) == null ? "" : getArguments().getString(IDatas.JsonKey.MARK);
        mAdapter.setMark(mark);
        fragmentCategoryBinding.swipeTarget.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        fragmentCategoryBinding.swipeTarget.setAdapter(mAdapter);
        fragmentCategoryBinding.category.setVisibility(View.GONE);
        hideAnimation = AnimationUtils.loadAnimation(
                getActivity(),
                R.anim.fragment_video_controller_hide);
    }

    @Override
    public void initEvents() {
        fragmentCategoryBinding.setPresenter(mPresenter);
        fragmentCategoryBinding.swipeTarget.setSwipeToLoadLayout(fragmentCategoryBinding.loadLayout);
        fragmentCategoryBinding.swipeTarget.setOnLoadMoreListener(this);
        hideAnimation.setAnimationListener(animListener);
        String mark = getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.MARK) == null ? "" : getArguments().getString(IDatas.JsonKey.MARK);
        String name = getArguments() == null ? "" : getArguments().getString(IDatas.JsonKey.NAME) == null ? "" : getArguments().getString(IDatas.JsonKey.NAME);
        fragmentCategoryBinding.title.setText(name);
        if (TextUtils.isEmpty(mark))
            mark = "video";
        mPresenter.loadFirstPageData(mark);
    }


    private Animation.AnimationListener animListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            hideFragment();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.mPresenter = (CategoryPresenter) presenter;
    }

    @Override
    public void back() {
        getActivity().finish();
    }

    @Override
    public void showOrder() {
        if (isShowFragment(orderMenuFragment)) {
            fragmentCategoryBinding.menuContain.startAnimation(hideAnimation);
        } else {
            showFragment(orderMenuFragment, true);
        }
    }

    @Override
    public void showCategory(boolean isChecked) {
        if (isShowFragment(categoryMenuFragment)) {
            fragmentCategoryBinding.menuContain.startAnimation(hideAnimation);
        } else {
            showFragment(categoryMenuFragment, true);
        }
    }

    @Override
    public void goSearchActivity() {
        getActivity().startActivity(SearchActivity.newIntent(getActivity()));
    }

    @Override
    public void updateData(List<PageBean.VideoContentBean> data) {
        if (mAdapter != null)
            mAdapter.setData(data);
    }

    @Override
    public FragmentLifecycleProvider getLifecycleProvider() {
        return this;
    }

    @Override
    public void updateCategory(CategoryModel model) {
        if (categoryMenuFragment != null && model != null) {
            fragmentCategoryBinding.category.setVisibility(View.VISIBLE);
            categoryMenuFragment.setCategoryData(model);
        }
    }

    @Override
    public void onLoadFinish() {
        fragmentCategoryBinding.swipeTarget.onLoadFinish();
    }

    @Override
    public void setOver(boolean b) {
        fragmentCategoryBinding.swipeTarget.setOver(b);
    }

    @Override
    public void showDefinitionDialog(List<DefinitionBean> data, int requestCode) {
        FragmentManager manager = getFragmentManager();
        DefinitionChoiceDialog dialog = DefinitionChoiceDialog
                .newInstance((ArrayList<DefinitionBean>) data);
        dialog.setTargetFragment(CategoryFragment.this, requestCode);
        dialog.show(manager, IDatas.JsonKey.DEFINITION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode==IDatas.Messages.REQUEST_PLAY){
            String url=data.getStringExtra(IDatas.JsonKey.ADDRESS);
            getActivity().startActivity(FullScreenPlayActivity.newIntent(getActivity(), url));
        }
    }

    @Override
    public void goVideoDetailActivity(String name) {
        getActivity().startActivity(VideoDetailActivity.newIntent(getActivity(),name));
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
