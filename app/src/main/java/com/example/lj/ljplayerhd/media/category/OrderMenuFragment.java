package com.example.lj.ljplayerhd.media.category;

import android.widget.RadioGroup;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.databinding.FragmentMenuOrderBinding;

/**
 * Created by wh on 2016/12/14.
 */

public class OrderMenuFragment extends BaseFragment {

    FragmentMenuOrderBinding orderBinding;
    CategoryPresenter mPresenter;

    public OrderMenuFragment(CategoryPresenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu_order;
    }

    @Override
    public void initViews() {
        orderBinding=FragmentMenuOrderBinding.bind(root);
        orderBinding.setPresenter(mPresenter);
        orderBinding.time.setChecked(true);
    }

    @Override
    public void initEvents() {

    }
}
