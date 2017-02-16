package com.example.lj.ljplayerhd.main.personal;


/**
 * Created by tlh on 2017/2/16.
 */

public class PersonalFragmentContract {

    public interface View {
        void setPresenter(PersonalFragmentContract.Presenter presenter);
    }

    public interface Presenter {
        void gotoLoginActivity();
    }
}
