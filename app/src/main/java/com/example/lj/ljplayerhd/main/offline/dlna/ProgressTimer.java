package com.example.lj.ljplayerhd.main.offline.dlna;

import android.content.Context;

import com.example.lj.ljplayerhd.main.offline.dlna.utils.AbstractTimer;


/**
 * Created by Tianluhua on 2017/1/4 0004.
 */

public class ProgressTimer extends AbstractTimer {

    public ProgressTimer(Context context) {
        super(context);
        setTimeInterval(1000);
    }
}
