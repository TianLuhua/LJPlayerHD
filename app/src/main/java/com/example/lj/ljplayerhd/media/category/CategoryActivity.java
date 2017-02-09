package com.example.lj.ljplayerhd.media.category;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.base.BaseSingleFragmentActivity;
import com.example.lj.ljplayerhd.config.IDatas;

/**
 * Created by wh on 2016/12/1.
 */

public class CategoryActivity extends BaseSingleFragmentActivity {

    private CategoryFragment categoryFragment;

    public static Intent newIntent(Context context,String mark,String name){
        Intent intent=new Intent(context,CategoryActivity.class);
        intent.putExtra(IDatas.JsonKey.MARK,mark);
        intent.putExtra(IDatas.JsonKey.NAME,name);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        String mark=getIntent().getStringExtra(IDatas.JsonKey.MARK);
        String name=getIntent().getStringExtra(IDatas.JsonKey.NAME);
        categoryFragment=CategoryFragment.newInstance(mark,name);
        new CategoryPresenter(categoryFragment);
        return categoryFragment;
    }
}
