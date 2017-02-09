package com.example.lj.ljplayerhd.main.offline.dlna;

import android.view.View;

/**
 * Created by Tianluhua on 2017/1/15 0015.
 */

public class DlanPresenter implements DlnaFragmentContract.Presenter {

    private DlnaFragmentContract.View view;

    public DlanPresenter(DlnaFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void refresh() {
        view.refresh();

    }

    @Override
    public void player(View v) {
        view.player(v);

    }

    @Override
    public void exit() {
        view.exit();
    }

    @Override
    public void fileBack() {
        view.fileBack();
    }
}
