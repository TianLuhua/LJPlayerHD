package com.example.lj.ljplayerhd.media.category;

import android.os.Bundle;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.CategoryModel;
import com.example.lj.ljplayerhd.databinding.FragmentMenuCategoryBinding;
import com.example.lj.ljplayerhd.widget.View_CategoryTableLayout;

import java.util.Map;

/**
 * Created by wh on 2016/12/14.
 */

public class CategoryMenuFragment extends BaseFragment implements View_CategoryTableLayout.OnCategoryClickListener {

    private CategoryPresenter mPresenter;
    FragmentMenuCategoryBinding binding;

    private CategoryMenuFragment(CategoryPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    public static CategoryMenuFragment newInstance(CategoryPresenter mPresenter, String mark) {
        Bundle args = new Bundle();
        args.putString("mark", mark);
        CategoryMenuFragment fragment = new CategoryMenuFragment(mPresenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu_category;
    }

    @Override
    public void initViews() {
        binding = FragmentMenuCategoryBinding.bind(root);
        String mark = getArguments() == null ? "" : getArguments().getString("mark") == null ? "" : getArguments().getString("mark");
        if ("video".equals(mark))
            mPresenter.loadCategoryData();
    }

    public void setCategoryData(CategoryModel model) {
        if (binding != null)
            binding.categoryTable.setData(model);
    }

    @Override
    public void initEvents() {
        if (binding != null)
            binding.categoryTable.setOnCategoryClickListener(this);
    }

    @Override
    public void onCategoryClick(Map<String, String> selected) {
        mPresenter.loadCategorySelected(selected);
    }
}
