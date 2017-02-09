package com.example.lj.ljplayerhd.main.offline.dlna;


import com.example.lj.ljplayerhd.main.offline.dlna.model.MediaItem;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.MediaManager;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.DlnaUtils;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.UpnpUtil;

public class MusicControllerActivity extends BaseMediaControllerActivity {

    @Override
    protected void initIntentData() {
        super.initIntentData();
        items = MediaManager.getInstance().getMusicList();
    }

    public void play(MediaItem item) {
        if (item != null && item.getRes() != null) {
            playActionManager.stop();
            String metaData = DlnaUtils.creatMetaData(item.getTitle(), UpnpUtil.DLNA_OBJECTCLASS_MUSICID);
            playActionManager.play(item.getRes(), metaData);
            progressTimer.stopTimer();
            progressTimer.startTimer();
            control_title.setText(item.getTitle());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
