package com.example.lj.ljplayerhd.main.personal;

import android.content.Context;
import android.content.Intent;

/**
 * Created by andy.shangguan on 2017/2/16.
 */

public class PersonalFragmentPresenter implements PersonalFragmentContract.Presenter {

    private PersonalFragmentContract.View view;
    private Context mContext;


    public PersonalFragmentPresenter(PersonalFragmentContract.View view, Context mContext) {
        this.view = view;
        this.mContext = mContext;
        view.setPresenter(this);
    }

    @Override
    public void gotoLoginActivity() {
        Intent intent = new Intent("LJ.action.login");
        mContext.startActivity(intent);
    }
}
