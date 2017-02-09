package com.example.lj.ljplayerhd.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.midi.MidiDevice;
import android.support.v4.app.ActivityCompat;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.bean.ConfigBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.offline.download.DownLoadService;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.SPUtil;
import com.yolanda.nohttp.rest.Response;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class MainPresenter implements MainContract.Presenter {

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    public static final String MY_PERMISSIONS_REQUEST_READ_CONTACTS_KEY = "MY_PERMISSIONS_REQUEST_READ_CONTACTS_KEY";

    private Activity mContext;
    private MainContract.View mView;


    public MainPresenter(Object o) {
        this.mContext = (Activity) o;
        this.mView = (MainContract.View) o;
    }

//    @Override
//    public void requestPermissions(int requestCode) {
//
//        ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                requestCode);
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                    //为获得权限
//                    SPUtil.put(mContext, MY_PERMISSIONS_REQUEST_READ_CONTACTS_KEY, 0);
//                } else {
//                    //获得权限
//                    SLogUtil.e("tlh", "获的PERMISSION_DENIED权限");
//                    SPUtil.put(mContext, MY_PERMISSIONS_REQUEST_READ_CONTACTS_KEY, 1);
//                }
//                return;
//            }
//        }
//    }

    @Override
    public void startDownLoadService(Context mainActivity) {
        Intent startDownLoadService = new Intent(mainActivity, DownLoadService.class);
        mainActivity.startService(startDownLoadService);

    }

    @Override
    public void stopDownLoadService(Context mainActivity) {
        Intent stopDownLoadService = new Intent(mainActivity, DownLoadService.class);
        mainActivity.stopService(stopDownLoadService);
    }

    @Override
    public void testConfig() {
        if (LJApplication.getConfig() == null) {
            RxNoHttp.requestJavaBean(mView.getActLife(), IDatas.WebService.API_ROOT_PATH, new Action1<Response<ConfigBean>>() {
                @Override
                public void call(Response<ConfigBean> response) {
                    if (response.get() == null)
                        mView.gotoBack();
                    else {
                        LJApplication.setConfig(response.get());
                    }
                }
            });
        }
    }
}
