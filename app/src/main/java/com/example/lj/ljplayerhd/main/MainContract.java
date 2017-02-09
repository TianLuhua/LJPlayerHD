package com.example.lj.ljplayerhd.main;

import android.content.Context;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.net.Authenticator;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class MainContract {


    public interface View {

        void gotoBack();

        ActivityLifecycleProvider getActLife();
    }

    public interface Presenter {

//        void requestPermissions(int request);
//
//        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

        void startDownLoadService(Context mainActivity);

        void stopDownLoadService(Context mainActivity);

        void testConfig();
    }
}
