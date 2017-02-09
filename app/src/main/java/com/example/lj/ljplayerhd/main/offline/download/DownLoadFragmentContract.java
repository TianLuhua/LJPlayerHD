package com.example.lj.ljplayerhd.main.offline.download;


import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;

import java.util.Map;

/**
 * Created by tlh on 2016/12/13 0013.
 */

public class DownLoadFragmentContract {

    public interface View {

        void upDataUI(Map<String, DownLoadTask> downLoadTasks);

        void cancelRefresh();

    }

    public interface Presenter {


        void onItemClick(DownLoadTask task);

        void reFreshData();


        void cancelRefresh();
    }
}
