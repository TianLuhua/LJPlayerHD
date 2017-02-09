package com.example.lj.ljplayerhd.main.offline.dlna;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lj.ljplayerhd.main.offline.dlna.center.PlayActionManager;
import com.example.lj.ljplayerhd.rxjava.RxBus;


/**
 * Created by tlh on 2017/1/3.
 */

public class BaseActivity extends AppCompatActivity {

    protected PlayActionManager playActionManager;
    protected RxBus rxBus;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playActionManager = PlayActionManager.getSingleTon();
        rxBus = RxBus.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxBus.unsubscribe();
    }
}
