package com.example.lj.ljplayerhd.main.offline.download;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.main.offline.download.Adapter.DownLoadTaskAdapter;
import com.example.lj.ljplayerhd.main.offline.download.db.LJDbManager;
import com.example.lj.ljplayerhd.main.offline.download.model.DownLoadTask;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/20 0020.
 * --------------视频播放/下载------------
 * /**
 * id : 207
 * mark : video
 * name : 新天师斗僵尸
 * picaddr : day_120412/F/density_55C6DD.jpg
 * year : 2011
 * director : 克雷格·吉勒斯佩
 * playactor : 安东·尤金,伊莫琴·普茨,柯林·法瑞尔,克里斯托夫·梅兹-普莱瑟
 * area : 欧美
 * type : 喜剧 恐怖
 * track : en
 * caption : 5C74A94B.srt
 * captionen :
 * timelen : 6364
 * address : /vod/207/207_480PH.mp4|/vod/207/207_480P.mp4|NULL
 * score : 8.0
 * snum : 0
 * <p>
 * 根路径：http://cloud.data.3dv.cn
 * 高清：http://cloud.data.3dv.cn/address[0]
 * 标清：http://cloud.data.3dv.cn/address[1]
 * 流畅：http://cloud.data.3dv.cn/address[2]
 */

public class DownLoadFragment extends BaseFragment implements DownLoadFragmentContract.View {

    private DownLoadFragmentContract.Presenter persenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DownLoadTaskAdapter adapter;
    private Map<String, DownLoadTask> tasks;
    private LJDbManager ljDbManager;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_persoanl_download;
    }

    @Override
    public void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.fragment_download_swiperefresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                persenter.reFreshData();
            }
        });
        recyclerView = (RecyclerView) root.findViewById(R.id.fragment_persnoal_local_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void initEvents() {
        ljDbManager = LJApplication.getLJDbManager();
        persenter = new DownLoadFragmengPresenter(this, ljDbManager);
        adapter = new DownLoadTaskAdapter(tasks, (DownLoadFragmengPresenter) persenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void upDataUI(Map<String, DownLoadTask> downLoadTasks) {
        adapter.setDownLoadTasks(downLoadTasks);
        adapter.notifyDataSetChanged();
        persenter.cancelRefresh();
    }

    @Override
    public void cancelRefresh() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

}
