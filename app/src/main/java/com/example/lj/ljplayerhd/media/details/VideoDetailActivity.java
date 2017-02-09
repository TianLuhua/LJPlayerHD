package com.example.lj.ljplayerhd.media.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.base.BaseSingleFragmentActivity;
import com.example.lj.ljplayerhd.config.IDatas;

/**
 * Created by wh on 2016/12/1.
 */

public class VideoDetailActivity extends BaseSingleFragmentActivity {

    public static Intent newIntent(Context context, String name) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        Bundle args = new Bundle();
        args.putString(IDatas.JsonKey.NAME, name);
        intent.putExtras(args);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        String name = getIntent().getExtras().getString(IDatas.JsonKey.NAME);
        VideoDetailsFragment videoDetailsFragment = VideoDetailsFragment.newInstance(name);
        new VideoDetailPresenter(videoDetailsFragment);
        return videoDetailsFragment;
    }
}
