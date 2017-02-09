package com.example.lj.ljplayerhd.main.firstpager.live;

import com.example.lj.ljplayerhd.bean.LiveBean;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.yolanda.nohttp.rest.Response;

import rx.functions.Action1;

/**
 * Created by wh on 2016/12/30.
 */

public class LivePresenter implements LiveContract.Presenter {

    private LiveContract.View mView;

    public LivePresenter(LiveContract.View view) {
        this.mView=view;
    }

    @Override
    public void itemClick(LiveBean.RoomBean bean) {
        if (bean != null)
            mView.goPlayerActivity(bean);
    }

    @Override
    public void loadLiveData(double longitude,double latitude) {
        String url="http://120.55.238.158/api/live/near_recommend?lc=3000000000011509&cv=IK3.1.10_Android&cc=TG36008&ua=XiaomiMI4LTE&uid=190761403&sid=20Tr1VWDFRc5wxUub4BC6rl55284NDi0VsCuvi2aZi2h59JJRfVDI&devi=867323029795190&imsi=460002330273772&imei=867323029795190&icc=898600520115f0989782&conn=WIFI&vv=1.0.3-2016060211417.android&aid=6524c2b6ae0bb697&osversion=android_23&mtid=4c8b78842db191e46d8639b709d1fa38&mtxid=fcd7333d06da&proto=4&smid=DujlPyXDfceh+88GbEzm+rhiRWdHAXcqw3ASJWkadmdHVmg5HpwVO2vPVauokUmZh2DI3gKxpE7rh4aEPx32U3LA";
        url+="&longitude="+114.06667+"&latitude="+22.61667;
        RxNoHttp.requestJavaBean(mView.getLifecycleProvider(), url, new Action1<Response<LiveBean>>() {
            @Override
            public void call(Response<LiveBean> response) {
                if(response.get()!=null)
                    mView.updateLiveData(response.get().getRooms());
            }
        });
    }
}
