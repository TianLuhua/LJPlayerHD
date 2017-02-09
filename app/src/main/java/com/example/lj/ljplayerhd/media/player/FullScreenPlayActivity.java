package com.example.lj.ljplayerhd.media.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.base.BaseSingleFragmentActivity;
import com.example.lj.ljplayerhd.config.IDatas;

/**
 * Created by wh on 2017/1/4.
 */

public class FullScreenPlayActivity extends BaseSingleFragmentActivity {

    private String url;

    public static Intent newIntent(Context context,String url){
        Intent intent=new Intent(context,FullScreenPlayActivity.class);
        intent.putExtra(IDatas.JsonKey.ADDRESS,url);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.url=getIntent().getStringExtra(IDatas.JsonKey.ADDRESS);
        if(url.endsWith("flv")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment createFragment() {
        FullscreenPlayFragment fragment=FullscreenPlayFragment.newInstance(url);
        return fragment;
    }
}
