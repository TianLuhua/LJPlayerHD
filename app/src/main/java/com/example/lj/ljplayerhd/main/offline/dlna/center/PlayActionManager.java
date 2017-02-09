package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import com.example.lj.ljplayerhd.main.offline.dlna.proxy.IController;
import com.example.lj.ljplayerhd.rxjava.RxBus;

import org.cybergarage.upnp.Device;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class PlayActionManager implements IController {

    private static final int PLAY = 0x001;
    private static final int GOON = 0x002;
    private static final int GETTRANSPORTSTATE = 0x003;
    private static final int GETMINVOLUME = 0x004;
    private static final int GETMAXVOLUME = 0x005;
    private static final int SEEK = 0x006;
    private static final int GETPOSITION = 0x007;
    private static final int GETDURATION = 0x008;
    private static final int SETMUTE = 0x009;
    private static final int GETMUTE = 0X010;
    private static final int SETVOICE = 0x011;
    private static final int GETVOICE = 0x012;
    private static final int STOP = 0x013;
    private static final int PAUSE = 0x014;
    private static final String ISSEEK = "isSeek";
    private static final String PROGRESS = "progress";
    private static final String ISSETMUTE = "isSetMute";
    private static final String MUTE = "mute";
    private static final String ISSETVOICE = "isSetVoice";
    private static final String VOICE = "voice";
    private static final String PATH = "path";
    private static final String METADATE = "metaData";
    private HandlerThread playThread;
    private static PlayActionManager singleton;
    private PlayControHandler controHandler;
    private MultiPointController pointController;
    private Device mDevice;
    private RxBus rxBus;

    private PlayActionManager() {
        //利用HandlerThread实现异步处理任务
        playThread = new HandlerThread("PlayActionManager");
        playThread.start();
        //将HandlerThread的Looper给到PlayControHandler，从而让PlayControHandler接受到的消息在HandlerThread内部进行异步
        controHandler = new PlayControHandler(playThread.getLooper());
        pointController = new MultiPointController();
        initRxBus();
    }

    private void initRxBus() {
        rxBus = RxBus.getInstance();
        rxBus.receive(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                Log.e("tlh", "PlayActionManager---Receive---Filter");
                return null;
            }
        }, new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.e("tlh", "PlayActionManager---Receive---OK");

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("tlh", "PlayActionManager---Receive---Error");

            }
        }, new Action0() {
            @Override
            public void call() {
                Log.e("tlh", "PlayActionManager---Receive---Complete");
            }
        });

    }

    public synchronized static PlayActionManager getSingleTon() {
        if (singleton == null)
            singleton = new PlayActionManager();
        return singleton;
    }

    public void setCurrentDevice(Device device) {
        this.mDevice = device;
    }

    private class PlayControHandler extends Handler {
        public PlayControHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mDevice == null)
                return;
            Message cbMessage = null;
            switch (msg.what) {
                case PLAY:
                    Bundle playData = msg.getData();
                    boolean isPlay = play(mDevice, playData.getString(PATH, ""),
                            playData.getString(METADATE, ""));
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = isPlay;
                    cbMessage.what = PLAY;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GOON:
                    boolean isGoon = goon(mDevice, msg.obj.toString());
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = isGoon;
                    cbMessage.what = GOON;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETTRANSPORTSTATE:
                    String transportState = getTransportState(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = transportState;
                    cbMessage.what = GETTRANSPORTSTATE;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETMAXVOLUME:
                    int maxVolume = getMaxVolumeValue(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = maxVolume;
                    cbMessage.what = GETMAXVOLUME;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETMINVOLUME:
                    int minVolume = getMinVolumeValue(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = minVolume;
                    cbMessage.what = GETMINVOLUME;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case SEEK:
                    boolean isSeek = seek(mDevice, msg.obj.toString());
                    cbMessage = callbackHandler.obtainMessage();
                    Bundle seekBundle = new Bundle();
                    seekBundle.putBoolean(ISSEEK, isSeek);
                    seekBundle.putString(PROGRESS, msg.obj.toString());
                    cbMessage.setData(seekBundle);
                    cbMessage.what = SEEK;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETPOSITION:
                    String position = getPositionInfo(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = position;
                    cbMessage.what = GETPOSITION;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETDURATION:
                    String duration = getMediaDuration(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = duration;
                    cbMessage.what = GETDURATION;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case SETMUTE:
                    boolean isSetMute = setMute(mDevice, msg.obj.toString());
                    cbMessage = callbackHandler.obtainMessage();
                    Bundle muteBundle = new Bundle();
                    muteBundle.putBoolean(ISSETMUTE, isSetMute);
                    muteBundle.putString(MUTE, msg.obj.toString());
                    cbMessage.setData(muteBundle);
                    cbMessage.what = SETMUTE;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETMUTE:
                    String mute = getMute(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = mute;
                    cbMessage.what = GETMUTE;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case SETVOICE:
                    boolean isSetVoice = setVoice(mDevice,
                            Integer.parseInt(msg.obj.toString()));
                    cbMessage = callbackHandler.obtainMessage();
                    Bundle voiceBundle = new Bundle();
                    voiceBundle.putBoolean(ISSETVOICE, isSetVoice);
                    voiceBundle.putInt(VOICE, Integer.parseInt(msg.obj.toString()));
                    cbMessage.setData(voiceBundle);
                    cbMessage.what = SETVOICE;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case GETVOICE:
                    int voice = getVoice(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = voice;
                    cbMessage.what = GETVOICE;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case STOP:
                    boolean isStop = stop(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = isStop;
                    cbMessage.what = STOP;
                    callbackHandler.sendMessage(cbMessage);
                    break;
                case PAUSE:
                    boolean isPause = pause(mDevice);
                    cbMessage = callbackHandler.obtainMessage();
                    cbMessage.obj = isPause;
                    cbMessage.what = PAUSE;
                    callbackHandler.sendMessage(cbMessage);
                    break;
            }
        }
    }

    private Handler callbackHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (uiCallBack == null)
                return;
            switch (msg.what) {
                case PLAY:
                    boolean isPlay = (Boolean) msg.obj;
                    uiCallBack.onPlay(isPlay);
                    break;
                case GOON:
                    boolean isGoon = (Boolean) msg.obj;
                    uiCallBack.onGoon(isGoon);
                    break;
                case GETTRANSPORTSTATE:
                    if (msg.obj == null)
                        break;
                    String transportState = msg.obj.toString();
                    uiCallBack.onTransportState(transportState);
                    break;
                case GETMAXVOLUME:
                    int maxVolume = (Integer) msg.obj;
                    uiCallBack.onMaxVolume(maxVolume);
                    break;
                case GETMINVOLUME:
                    int minVolume = (Integer) msg.obj;
                    uiCallBack.onMinVolume(minVolume);
                    break;
                case SEEK:
                    Bundle seekData = msg.getData();
                    boolean isSeek = seekData.getBoolean(ISSEEK);
                    String progress = seekData.getString(PROGRESS);
                    uiCallBack.onSeekTo(isSeek, progress);
                    break;
                case GETPOSITION:
                    if (msg.obj == null)
                        break;
                    String position = msg.obj.toString();
                    uiCallBack.onPosition(position);
                    break;
                case GETDURATION:
                    if (msg.obj == null)
                        break;
                    String duration = msg.obj.toString();
                    uiCallBack.onDuration(duration);
                    break;
                case SETMUTE:
                    Bundle muteBundle = msg.getData();
                    boolean isSetmute = muteBundle.getBoolean(ISSETMUTE);
                    String muteString = muteBundle.getString(MUTE);
                    uiCallBack.onMuteSet(isSetmute, muteString);
                    break;
                case GETMUTE:
                    if (msg.obj == null)
                        break;
                    String mute = msg.obj.toString();
                    uiCallBack.onMute(mute);
                    break;
                case SETVOICE:
                    Bundle voiceBundle = msg.getData();
                    boolean isSetvoice = voiceBundle.getBoolean(ISSETVOICE);
                    int vc = voiceBundle.getInt(VOICE);
                    uiCallBack.onVoiceSet(isSetvoice, vc);
                    break;
                case GETVOICE:
                    int voice = (Integer) msg.obj;
                    uiCallBack.onVoice(voice);
                    break;
                case STOP:
                    boolean isStop = (Boolean) msg.obj;
                    uiCallBack.onStop(isStop);
                    break;
                case PAUSE:
                    boolean isPause = (Boolean) msg.obj;
                    uiCallBack.onPause(isPause);
                    break;
            }
        }

        ;
    };

    public interface UpdateUiCallBack {
        void onPlay(boolean isPlay);

        void onGoon(boolean isGoon);

        void onTransportState(String transportState);

        void onMinVolume(int minVolume);

        void onMaxVolume(int maxVolume);

        void onSeekTo(boolean isSeek, String progress);

        void onPosition(String position);

        void onDuration(String duration);

        void onMuteSet(boolean isSetmute, String mute);

        void onMute(String mute);

        void onVoiceSet(boolean isSetvoice, int voice);

        void onVoice(int voice);

        void onStop(boolean isStop);

        void onPause(boolean isPause);
    }

    private UpdateUiCallBack uiCallBack;

    public void setUpdateUiCallBack(UpdateUiCallBack callBack) {
        this.uiCallBack = callBack;
    }

    public void onStop() {
//     playThread.quitSafely();
    }

    public void play(String path, String metaData) {
        Message msg = controHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString(PATH, path);
        bundle.putString(METADATE, metaData);
        msg.setData(bundle);
        msg.what = PLAY;
        controHandler.sendMessage(msg);
    }

    public void goon(String pausePosition) {
        Message msg = controHandler.obtainMessage();
        msg.obj = pausePosition;
        msg.what = GOON;
        controHandler.sendMessage(msg);
    }

    public void getTransportState() {
        controHandler.sendEmptyMessage(GETTRANSPORTSTATE);
    }

    public void getMinVolume() {
        controHandler.sendEmptyMessage(GETMINVOLUME);
    }

    public void getMaxVolume() {
        controHandler.sendEmptyMessage(GETMAXVOLUME);
    }

    public void seekTo(String targetPosition) {
        Message msg = controHandler.obtainMessage();
        msg.obj = targetPosition;
        msg.what = SEEK;
        controHandler.sendMessage(msg);
    }

    //获取当前的播放事件
    public void getPosition() {
        controHandler.sendEmptyMessage(GETPOSITION);
    }

    //获取当前播放资源的总事件
    public void getDuration() {
        controHandler.sendEmptyMessage(GETDURATION);
    }

    public void setMute(String targetValue) {
        Message msg = controHandler.obtainMessage();
        msg.obj = targetValue;
        msg.what = SETMUTE;
        controHandler.sendMessage(msg);
    }

    public void getMute() {
        controHandler.sendEmptyMessage(GETMUTE);
    }

    public void setVoice(int voice) {
        Message msg = controHandler.obtainMessage();
        msg.obj = voice;
        msg.what = SETVOICE;
        controHandler.sendMessage(msg);
    }

    public void getVoice() {
        controHandler.sendEmptyMessage(GETVOICE);
    }

    public void stop() {
        controHandler.sendEmptyMessage(STOP);
    }

    public void pause() {
        controHandler.sendEmptyMessage(PAUSE);
    }

    public void play() {
        controHandler.sendEmptyMessage(PLAY);
    }


    @Override
    public synchronized boolean play(Device device, String path, String metaData) {
        return pointController.play(device, path, metaData);
    }

    @Override
    public synchronized boolean goon(Device device, String pausePosition) {
        return pointController.goon(device, pausePosition);
    }

    @Override
    public synchronized String getTransportState(Device device) {
        return pointController.getTransportState(device);
    }

    @Override
    public synchronized int getMinVolumeValue(Device device) {
        return pointController.getMinVolumeValue(device);
    }

    @Override
    public synchronized int getMaxVolumeValue(Device device) {
        return pointController.getMaxVolumeValue(device);
    }

    @Override
    public synchronized boolean seek(Device device, String targetPosition) {
        return pointController.seek(device, targetPosition);
    }

    @Override
    public synchronized String getPositionInfo(Device device) {
        return pointController.getPositionInfo(device);
    }

    @Override
    public synchronized String getMediaDuration(Device device) {
        return pointController.getMediaDuration(device);
    }

    @Override
    public synchronized boolean setMute(Device device, String targetValue) {
        return pointController.setMute(device, targetValue);
    }

    @Override
    public synchronized String getMute(Device device) {
        return pointController.getMute(device);
    }

    @Override
    public synchronized boolean setVoice(Device device, int value) {
        return pointController.setVoice(device, value);
    }

    @Override
    public synchronized int getVoice(Device device) {
        return pointController.getVoice(device);
    }

    @Override
    public synchronized boolean stop(Device device) {
        return pointController.stop(device);
    }

    @Override
    public synchronized boolean pause(Device device) {
        return pointController.pause(device);
    }
}
