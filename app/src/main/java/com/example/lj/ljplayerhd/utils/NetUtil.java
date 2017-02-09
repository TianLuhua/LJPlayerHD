package com.example.lj.ljplayerhd.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.config.IDatas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class NetUtil {

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivityForResult(intent, 0);
    }

    public static List<DefinitionBean> getPlayUrl(String address){
        List<DefinitionBean> data=new ArrayList<>();
        if(address==null)
            return data;
        String[] strings = address.split("\\|");
        for (int i = 0; i < strings.length; i++) {
            if(!"NULL".equalsIgnoreCase(strings[i])){
                Log.e("info","strings:"+strings[i]);
                if(i==0){
                    DefinitionBean bean=new DefinitionBean();
                    bean.setDefinition("高清");
                    bean.setUrl(IDatas.WebService.VIDEO_PATH+strings[i]);
                    data.add(bean);
                }
                if (i==1){
                    DefinitionBean bean1 = new DefinitionBean();
                    bean1.setDefinition("标清");
                    bean1.setUrl(IDatas.WebService.VIDEO_PATH+strings[i]);
                    data.add(bean1);
                }
                if(i==2){
                    DefinitionBean bean2 = new DefinitionBean();
                    bean2.setDefinition("流畅");
                    bean2.setUrl(IDatas.WebService.VIDEO_PATH+strings[i]);
                    data.add(bean2);
                }
            }
        }
        return data;
    }

    //获取电影的后缀
    public static String getVideoSuffix(String url, String videoName) {
        SLogUtil.e("tlh", "url:" + url);
        if (url != null && url.contains(".")) {
            String ss[] = url.split("\\.");
            SLogUtil.e("tlh", "后缀：" + "." + ss[ss.length - 1]);
            return videoName + "." + ss[ss.length - 1];
        }
        SLogUtil.e("tlh", "电影名称不合法！");
        return null;

    }
}
