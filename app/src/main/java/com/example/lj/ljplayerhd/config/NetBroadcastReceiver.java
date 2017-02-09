package com.example.lj.ljplayerhd.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.yolanda.nohttp.tools.NetUtil;

/**
 * Created by wh on 2016/11/26.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                if(LJApplication.getConfig() == null)
                        LJApplication.initConfig();
                    String name = networkInfo.getTypeName();
                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_MOBILE://移动 网络
                            ToastUtil.showShortGravity(R.string.net_type_mobile,Gravity.CENTER,0,0);
                            break;
                        case ConnectivityManager.TYPE_WIFI://wifi网络
                            ToastUtil.showShortGravity(R.string.net_type_wifi,Gravity.CENTER,0,0);
                            break;
                }
            } else {// 无网络
                //network was unavailable, stop the streamer, and show a dialog
                ToastUtil.showLongGravity(R.string.error_not_network, Gravity.CENTER,0,0);
            }
        }
    }
}
