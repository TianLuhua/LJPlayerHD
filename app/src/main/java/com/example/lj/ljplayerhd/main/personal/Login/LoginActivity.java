package com.example.lj.ljplayerhd.main.personal.Login;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseMultipleFragmentActivity;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by tlh on 2016/12/1 0001.
 */

public class LoginActivity extends BaseMultipleFragmentActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initEvents() {
        RxBus.with(this).receive(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if (o instanceof Integer) {
                   return true;
                }
                return false;
            }
        }, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if ((int) o == IDatas.Messages.START_REGISTER) {
                    showFragment(registerFragment);
                    SLogUtil.e("info", "registerFragment");
                } else if ((int) o == IDatas.Messages.RETRUN_LOGIN) {
                    showFragment(loginFragment);
                    SLogUtil.e("info", "loginFragment");
                }
            }
        }, null);


    }

    @Override
    public List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();
        fragments.add(loginFragment);
        fragments.add(registerFragment);
        return fragments;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_singlefragment;
    }

    @Override
    protected int getFragmentId() {
        return R.id.single_fragment_contain;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
