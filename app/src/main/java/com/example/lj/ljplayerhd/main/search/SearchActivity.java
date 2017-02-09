package com.example.lj.ljplayerhd.main.search;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.lj.ljplayerhd.base.BaseSingleFragmentActivity;

/**
 * Created by wh on 2016/11/21.
 */

public class SearchActivity extends BaseSingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        SearchFragment fragment = new SearchFragment();
        fragment.setCancelVisibility(View.VISIBLE);
        return fragment;
    }

    public static Intent newIntent(Context activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        return intent;
    }
}
