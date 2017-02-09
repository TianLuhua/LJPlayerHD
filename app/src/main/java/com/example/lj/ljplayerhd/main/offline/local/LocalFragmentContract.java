package com.example.lj.ljplayerhd.main.offline.local;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lj.ljplayerhd.bean.LocalVedio;

import java.util.List;

/**
 * Created by Tianlhua on 2016/11/24 0024.
 */

public class LocalFragmentContract {

    public interface View {


        void goPlayerActivity(String path);

        Context getContext();

        void updateData(List<LocalVedio> localVedios);
        void canCelRefresh();
    }

    public interface Presenter {
        void setRecyclerViewAdapter();

        void onItemClick(String path);

        void onRefresh();

        void canCelRefresh();
    }
}
