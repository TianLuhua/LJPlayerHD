package com.example.lj.ljplayerhd.main.offline.local;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.LocalVedio;
import com.example.lj.ljplayerhd.main.MainPresenter;
import com.example.lj.ljplayerhd.main.offline.local.adapter.LocalFragmentAdapter;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.SPUtil;

import java.util.List;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tinaluhua on 2016/11/24 0024.
 */

public class LocalFragmentPresenter implements LocalFragmentContract.Presenter {

    private LocalFragmentContract.View mView;
    private Context mContext;

    public LocalFragmentPresenter(LocalFragmentContract.View view, Context mContext) {
        this.mView = view;
        this.mContext = mContext;
    }

    @Override
    public void setRecyclerViewAdapter() {
        ILocalVideoProvider localVedioProcider = new LocalVideoProvider(mView.getContext());
        Observable.just(localVedioProcider)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ILocalVideoProvider, List<LocalVedio>>() {
                    @Override
                    public List<LocalVedio> call(ILocalVideoProvider iLocalVideoProvider) {
                        return iLocalVideoProvider.getList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LocalVedio>>() {
                    @Override
                    public void call(List<LocalVedio> localVedios) {
                        mView.updateData(localVedios);
                    }
                });


    }

    @Override
    public void onItemClick(String path) {
        SLogUtil.e("tlh", path);
        mView.goPlayerActivity(path);
    }

    @Override
    public void onRefresh() {
        //// TODO: 2017/1/16 0016  发送广播通知系统扫描服务去跟新媒体库

    }

    @Override
    public void canCelRefresh() {
        mView.canCelRefresh();
    }
}
