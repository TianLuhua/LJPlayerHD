package com.example.lj.ljplayerhd.main.offline.dlna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.main.offline.dlna.center.MediaItemFactory;
import com.example.lj.ljplayerhd.main.offline.dlna.center.PlayActionManager;
import com.example.lj.ljplayerhd.main.offline.dlna.model.MediaItem;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.TimeFormatUtil;

import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public abstract class BaseMediaControllerActivity extends BaseActivity implements PlayActionManager.UpdateUiCallBack {

    protected TextView duration;
    protected TextView currTime;
    protected SeekBar seekBar;
    protected FrameLayout fl_volume;
    protected ImageView iv_volume;
    protected ImageView iv_mute;
    protected SeekBar sb_voice;
    protected ImageView control_bk;
    protected TextView control_title;
    protected List<MediaItem> items;
    protected int currPosition;
    protected Button mediaPlayButton;
    protected static final int SHOW_PROGRESS = 0x001;
    private static final String MUTE = "1";
    private static final String UNMUTE = "0";
    protected ProgressTimer progressTimer;
    public static final String POSITION = "position";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_controller);

        playActionManager = PlayActionManager.getSingleTon();
        //设置回调接口，更新
        playActionManager.setUpdateUiCallBack(this);
        //初始化ExBus start
        initRxBus();
        //初始化ExBus end
        progressTimer = new ProgressTimer(BaseMediaControllerActivity.this);
//        progressTimer.setHandler(mHandler, SHOW_PROGRESS);

        duration = (TextView) findViewById(R.id.media_control_duration);
        currTime = (TextView) findViewById(R.id.media_control_currtime);
        fl_volume = (FrameLayout) findViewById(R.id.fl_volume);
        iv_mute = (ImageView) findViewById(R.id.iv_mute);
        iv_volume = (ImageView) findViewById(R.id.iv_volume);
        control_bk = (ImageView) findViewById(R.id.media_control_bk);
        control_title = (TextView) findViewById(R.id.media_control_title);
        mediaPlayButton = (Button) findViewById(R.id.media_control_play);
        seekBar = (SeekBar) findViewById(R.id.media_control_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e("tlh", "onProgressChanged:" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("tlh", "onStartTrackingTouch:");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("tlh", "onStopTrackingTouch:"+TimeFormatUtil.secToTime(seekBar.getProgress()));
                playActionManager.seekTo(TimeFormatUtil.secToTime(seekBar.getProgress()));

            }
        });
        sb_voice = (SeekBar) findViewById(R.id.sb_voice);
        sb_voice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (progress == 0) {
                    iv_mute.setVisibility(View.VISIBLE);
                    iv_volume.setVisibility(View.GONE);
                } else {
                    iv_mute.setVisibility(View.GONE);
                    iv_volume.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setVoice(seekBar.getProgress());

            }
        });
        initIntentData();
    }

    private void initRxBus() {
        rxBus.receive(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                Log.e("tlh", "BaseMediaControllerActivity---Receive---Filter");
                return null;
            }
        }, new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.e("tlh", "BaseMediaControllerActivity---Receive---OK");

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("tlh", "BaseMediaControllerActivity---Receive---Error");

            }
        }, new Action0() {
            @Override
            public void call() {
                Log.e("tlh", "BaseMediaControllerActivity---Receive---Complete");
            }
        });
    }

    protected void initIntentData() {
        Intent intent = getIntent();
        MediaItem item = MediaItemFactory.getItemFromIntent(intent);
        currPosition = intent.getIntExtra(POSITION, 0);
        play(item);

    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS:
                    break;
            }
        }
    };

    public abstract void play(MediaItem item);


    public void setMute(View v) {
        String targetValue = MUTE;
        if (iv_mute.getVisibility() == View.VISIBLE) {
            targetValue = UNMUTE;
            iv_mute.setVisibility(View.GONE);
            iv_volume.setVisibility(View.VISIBLE);
        } else {
            iv_mute.setVisibility(View.VISIBLE);
            iv_volume.setVisibility(View.GONE);
            sb_voice.setProgress(0);
        }
        playActionManager.setMute(targetValue);
    }

    private synchronized void setVoice(final int voice) {
        sb_voice.setProgress(voice);
        new Thread() {
            @Override
            public void run() {
                playActionManager.setVoice(voice);
            }
        }.start();

    }


    /**
     * 播放/暂停按钮
     */
    public void doResumePlay(View v) {
        if (isPlaying) {

            playActionManager.pause();
            return;
        }
        if (!isPlaying) {
            String pausePosition = currTime.getText().toString().trim();
            goon(pausePosition);
        } else {
            playActionManager.play();
        }

    }

    private synchronized void goon(final String pausePosition) {
        new Thread() {
            @Override
            public void run() {
                playActionManager.goon(
                        pausePosition);
            }
        }.start();
    }

    //播放下一条目
    public void next(View v) {
        currPosition += 1;
        if (currPosition < items.size()) {
            MediaItem item = items.get(currPosition);
            play(item);
        } else {
            currPosition = items.size() - 1;
        }
    }

    //播放上一条目
    public void previous(View v) {
        currPosition -= 1;
        if (currPosition >= 0) {
            MediaItem item = items.get(currPosition);
            play(item);
        } else {
            currPosition = 0;
        }
    }

    private boolean isPlaying = false;

    @Override
    public void onPlay(boolean isPlay) {
        isPlaying = isPlay;
        Log.e("info", "isPlaying:" + isPlay);
        if (isPlay)
            playActionManager.getDuration();
        mHandler.sendEmptyMessage(SHOW_PROGRESS);
    }


    @Override
    public void onGoon(boolean isGoon) {
        if (isGoon) {
            isPlaying = true;
            mediaPlayButton.setBackgroundResource(R.drawable.btn_pause_n);
        }
    }

    @Override
    public void onTransportState(String transportState) {
        Log.e("tlh", "transportState:" + transportState);
    }

    @Override
    public void onMinVolume(int minVolume) {
        Log.e("tlh", "minVolume:" + minVolume);
    }

    @Override
    public void onMaxVolume(int maxVolume) {
        Log.e("tlh", "maxVolume:" + maxVolume);
    }

    @Override
    public void onSeekTo(boolean isSeek, String progress) {
        if (isSeek) {
            seekBar.setProgress(Integer.parseInt(progress));
        }
    }

    @Override
    public void onPosition(String position) {
        Log.e("tlh", "onPosition:" + position);
        currTime.setText(position);
        handler.sendEmptyMessageDelayed(3, 800);
    }

    @Override
    public void onDuration(String duration) {
        Log.e("tlh", "duration:" + duration);
        this.duration.setText(duration);
        handler.sendEmptyMessageDelayed(3, 800);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    playActionManager.getPosition();
                    break;
            }
        }
    };

    @Override
    public void onMuteSet(boolean isSetmute, String mute) {
        Log.e("tlh", "isSetmute:" + isSetmute + ",mute:" + mute);
        if (isSetmute) {
            if (MUTE.equals(mute)) {
                iv_mute.setVisibility(View.VISIBLE);
                iv_volume.setVisibility(View.GONE);
                sb_voice.setProgress(0);
            } else if (UNMUTE.equals(mute)) {
                iv_mute.setVisibility(View.GONE);
                iv_volume.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onMute(String mute) {
        Log.e("tlh", "mute:" + mute);
    }

    @Override
    public void onVoiceSet(boolean isSetvoice, int voice) {
        Log.e("tlh", "isSetvoice:" + isSetvoice + ",voice:" + voice);
        if (isSetvoice) {
            iv_mute.setVisibility(View.GONE);
            iv_volume.setVisibility(View.VISIBLE);
            sb_voice.setProgress(voice);
        }
    }

    @Override
    public void onVoice(int voice) {
        if (voice <= 0) {
//            speaker.setImageResource(R.drawable.mute);
        } else {
//            speaker.setImageResource(R.drawable.speaker);
        }
    }

    @Override
    public void onStop(boolean isStop) {
        if (isStop) {
            isPlaying = false;
        }
    }

    @Override
    public void onPause(boolean isPause) {
        if (isPause) {
            isPlaying = false;
            mediaPlayButton.setBackgroundResource(R.drawable.btn_play_n);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
