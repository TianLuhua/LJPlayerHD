package com.example.lj.ljplayerhd.main.offline.dlna;

import android.os.Bundle;
import android.util.Log;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.main.offline.dlna.model.MediaItem;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.MediaManager;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.DlnaUtils;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.UpnpUtil;


public class VideoControllerActivity extends BaseMediaControllerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        control_bk.setImageResource(R.drawable.video_controller_bk);
    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
        items = MediaManager.getInstance().getVideoList();
    }

    @Override
    public void play(MediaItem item) {
        if (item != null && item.getRes() != null) {
            String metaData = DlnaUtils.creatMetaData(item.getTitle(), UpnpUtil.DLNA_OBJECTCLASS_VIDEOID);
            playActionManager.play(item.getRes(), metaData);
            playActionManager.getDuration();
            progressTimer.stopTimer();
            progressTimer.startTimer();
            Log.e("tlh","VideoControllerActivity---play");
            control_title.setText(item.getTitle());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
