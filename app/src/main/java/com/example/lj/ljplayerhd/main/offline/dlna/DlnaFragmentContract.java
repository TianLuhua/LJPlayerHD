package com.example.lj.ljplayerhd.main.offline.dlna;


import android.view.View;

/**
 * Created by Tianluhua on 2017/1/15 0015.
 */

public class DlnaFragmentContract {

    public interface View {

        void refresh();

        void player(android.view.View v);

        void exit();

        void fileBack();

    }

    public interface Presenter {

        void refresh();

        void player(android.view.View v);

        void exit();

        void fileBack();

    }
}
