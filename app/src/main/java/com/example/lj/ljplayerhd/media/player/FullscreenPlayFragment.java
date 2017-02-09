package com.example.lj.ljplayerhd.media.player;

import android.databinding.Observable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.FragmentFullplayBinding;
import com.example.lj.ljplayerhd.opengl.VideoFormatContext;

/**
 * Created by wh on 2016/11/19.
 */

public class FullscreenPlayFragment extends BaseFragment implements Runnable{

    FragmentFullplayBinding fullPlayBinding;

    public static FullscreenPlayFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(IDatas.JsonKey.ADDRESS, url);
        FullscreenPlayFragment fragment = new FullscreenPlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fullplay;
    }

    @Override
    public void initViews() {
        fullPlayBinding = FragmentFullplayBinding.bind(root);
        Log.e("info", "ADDRESS:" + getArguments().getString(IDatas.JsonKey.ADDRESS));
        fullPlayBinding.video.setVideoPath(getArguments().getString(IDatas.JsonKey.ADDRESS));
    }

    @Override
    public void initEvents() {
        fullPlayBinding.videoDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullPlayBinding.video.setFormatType(VideoFormatContext.VIDEO_DEFAULT);
            }
        });
        fullPlayBinding.videoDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullPlayBinding.video.setFormatType(VideoFormatContext.VIDEO_DOUBLE);
            }
        });

        fullPlayBinding.videoHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullPlayBinding.video.setFormatType(VideoFormatContext.VIDEO_HALF);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        fullPlayBinding.video.release(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fullPlayBinding != null && fullPlayBinding.video != null)
            new Thread(this).start();
    }

    @Override
    protected void saveState() {
        super.saveState();
        if (fullPlayBinding != null && fullPlayBinding.video != null)
            fullPlayBinding.video.pause();
    }

    @Override
    protected void recoverState() {
        super.recoverState();
        if (fullPlayBinding != null && fullPlayBinding.video != null)
            fullPlayBinding.video.start();
    }

    @Override
    public void run() {

    }
}
