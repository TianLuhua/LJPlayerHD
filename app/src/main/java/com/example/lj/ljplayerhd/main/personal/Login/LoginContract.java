package com.example.lj.ljplayerhd.main.personal.Login;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.trello.rxlifecycle.FragmentLifecycleProvider;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class LoginContract {

    public interface LogFragmentView {

        public String getUserName();

        public String getPassword();

        public FragmentLifecycleProvider getFragmentLifecycleProvider();

        public void showProgress(boolean b);
    }

    public interface LogFragmentPresenter {

        public void login();

        public void rememberPassword(boolean rememberPassword);

        public void shoewRegisterFragment();
    }

    public interface RegisterFragmentView {

        public String getUserName();

        public String getPassword();

        public FragmentLifecycleProvider getFragmentLifecycleProvider();

        public void showProgress(boolean show);


    }

    public interface RegisterFragmentPresenter {

        public void register();

        public void regiterReturnLogin();
    }


}
