package com.example.lj.ljplayerhd.main.firstpager.live;

import com.example.lj.ljplayerhd.bean.LiveBean;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.List;

/**
 * Created by wh on 2016/12/30.
 */

public class LiveContract {
    
    public interface View{
        
        void updateLiveData(List<LiveBean.RoomBean> bean);
        
        FragmentLifecycleProvider getLifecycleProvider();

        void goPlayerActivity(LiveBean.RoomBean bean);
    }

    public interface Presenter{

        void itemClick(LiveBean.RoomBean bean);

        void loadLiveData(double longitude,double latitude);
    }
}
