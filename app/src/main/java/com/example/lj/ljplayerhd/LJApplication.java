package com.example.lj.ljplayerhd;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.example.lj.ljplayerhd.bean.ConfigBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.offline.download.db.LJDbManager;
import com.example.lj.ljplayerhd.rxjava.Message;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.URLConnectionNetworkExecutor;
import com.yolanda.nohttp.cache.DBCacheStore;
import com.yolanda.nohttp.cookie.DBCookieStore;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.tools.NetUtil;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class LJApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static ConfigBean instance;
    private static LJDbManager ljDbManager;

    public static ConfigBean getConfig() {
        return instance;
    }

    public static void setConfig(ConfigBean instance) {
        if (!TextUtils.isEmpty(instance.getServer()))
            IDatas.WebService.VIDEO_PATH = instance.getServer();
        if (!TextUtils.isEmpty(instance.getThumbPath()))
            IDatas.WebService.THUMB_PATH = instance.getThumbPath();
        if (!TextUtils.isEmpty(instance.getSubtitlePath()))
            IDatas.WebService.SUBTITLE_PATH = instance.getSubtitlePath();
        Log.e("info", "video_path:" + instance.getServer());
        LJApplication.instance = instance;
        Message msg = new Message();
        RxBus.getInstance().send(msg);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        SLogUtil.isDebug = true;
        initNoHttp();
        initAPI();
        initLJDB();
    }


    //初始化下载数据库
    private void initLJDB() {
        if (ljDbManager == null) {
            ljDbManager = new LJDbManager(LJApplication.this);
        }
    }

    public static LJDbManager getLJDbManager() {
        return ljDbManager;
    }

    private void initAPI() {
        //初始化api
        IDatas.WebService.UpdateGlobalSettingsFromServer(this);
        initConfig();
    }

    public static void initConfig() {
        RxNoHttp.request(IDatas.WebService.API_ROOT_PATH, ConfigSubscribe);
    }

    private static Action1<Response<ConfigBean>> ConfigSubscribe = new Action1<Response<ConfigBean>>() {
        @Override
        public void call(Response<ConfigBean> response) {
            Log.e("info", "Response:" + response.get());
            if (response.get() != null && getConfig() == null)
                setConfig(response.get());
        }
    };

    private void initNoHttp() {
        NoHttp.Config config = new NoHttp.Config();
        config.setConnectTimeout(5 * 1000);
        config.setReadTimeout(5 * 1000);
        config.setCacheStore(new DBCacheStore(this).setEnable(false));
        config.setCookieStore(new DBCookieStore(this).setEnable(false));
        config.setNetworkExecutor(new URLConnectionNetworkExecutor());
        NoHttp.initialize(this, config);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        SLogUtil.e("info", "throwable:" + throwable.getMessage().toString() + "\n" + "thread：" + thread.currentThread().getName());

    }
}
