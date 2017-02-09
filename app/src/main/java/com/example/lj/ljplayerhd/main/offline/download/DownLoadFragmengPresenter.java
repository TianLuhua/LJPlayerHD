package com.example.lj.ljplayerhd.main.offline.download;

import com.example.lj.ljplayerhd.main.offline.download.db.LJDbManager;
import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.utils.SLogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class DownLoadFragmengPresenter implements DownLoadFragmentContract.Presenter {

    public static final String TAG = DownLoadFragmengPresenter.class.getSimpleName();

    private DownLoadFragmentContract.View view;
    private RxBus rxBus;
    private Map<String, DownLoadTask> downLoadTasks;
    private LJDbManager ljDbManager;

    public DownLoadFragmengPresenter(DownLoadFragmentContract.View view, LJDbManager ljDbManager) {
        this.view = view;
        this.ljDbManager = ljDbManager;
        this.rxBus = RxBus.getInstance();
        downLoadTasks = new HashMap<String, DownLoadTask>();
        initReceive();
        upDataUI();
    }


    private void initReceive() {
        rxBus.receive(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if (o instanceof Integer) {
                    SLogUtil.e("tlh", "55555555555555:" + System.nanoTime());
                    return true;
                }
                return false;
            }
        }, new Action1<Object>() {
            @Override
            public void call(Object o) {
                upDataUI();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                SLogUtil.e("info", "DownLoadFragmengPresenter--" + "出错了！");
            }
        }, new Action0() {
            @Override
            public void call() {
                SLogUtil.e("info", "DownLoadFragmengPresenter--" + "结束了！");
            }
        });

    }

    private void upDataUI() {

        Observable<List<DownLoadTask>> tasks = ljDbManager.queryDownLoadTask();
        tasks.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<DownLoadTask>>() {

            @Override
            public void onCompleted() {
                SLogUtil.e("tlh", TAG + "：从数据库获取数据完成！");
            }

            @Override
            public void onError(Throwable e) {
                SLogUtil.e("tlh", TAG + "：从数据库获取数据失败！");
            }

            @Override
            public void onNext(List<DownLoadTask> tasks) {
                SLogUtil.e("tlh", "66666666666:" + System.nanoTime());
                for (DownLoadTask t : tasks) {
                    downLoadTasks.put(String.valueOf(t.getId()), t);
                }
                view.upDataUI(downLoadTasks);
            }
        });

    }



    @Override
    public void onItemClick(DownLoadTask task) {
        SLogUtil.e("tlh", "Task:" + task.toString());
    }

    @Override
    public void reFreshData() {
        view.upDataUI(downLoadTasks);
    }

    @Override
    public void cancelRefresh() {
        view.cancelRefresh();
    }

}
