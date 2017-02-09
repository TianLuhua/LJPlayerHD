package com.example.lj.ljplayerhd.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

/**
 * Created by wh on 2016/11/19.
 */

public abstract class BaseSingleFragmentActivity extends RxFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SLogUtil.e("info", "BaseSingleFragmentActivity---onCreate");
        setContentView(getLayoutID());
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(getFragmentId());
        if (fragment == null) {
            SLogUtil.e("info", "fragment == null");
            fragment = createFragment();
            manager.beginTransaction().add(getFragmentId(), fragment).commit();
        }
    }

    public abstract Fragment createFragment();

    protected int getLayoutID() {
        return R.layout.activity_singlefragment;
    }

    protected int getFragmentId() {
        return R.id.single_fragment_contain;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
