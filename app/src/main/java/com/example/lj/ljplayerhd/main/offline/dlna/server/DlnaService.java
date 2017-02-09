package com.example.lj.ljplayerhd.main.offline.dlna.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


import com.example.lj.ljplayerhd.main.offline.dlna.center.ControlCenterWorkThread;
import com.example.lj.ljplayerhd.main.offline.dlna.center.IBaseEngine;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.AllShareProxy;

import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.DeviceChangeListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.cybergarage.util.CommonLog;
import org.cybergarage.util.LogFactory;

public class DlnaService extends Service implements IBaseEngine,
		DeviceChangeListener, ControlCenterWorkThread.ISearchDeviceListener {

	private static final CommonLog log = LogFactory.createLog();

	public static final String SEARCH_DEVICES = "com.geniusgithub.allshare.search_device";
	public static final String RESET_SEARCH_DEVICES = "com.geniusgithub.allshare.reset_search_device";

	private static final int NETWORK_CHANGE = 0x0001;
	private boolean firstReceiveNetworkChangeBR = true;
	private NetworkStatusChangeBR mNetworkStatusChangeBR;

	private ControlPoint mControlPoint;
	private ControlCenterWorkThread mCenterWorkThread;
	// 代理DlnaService
	private AllShareProxy mAllShareProxy;
	//
	private Handler mHandler;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		log.e("DlnaService onCreate");
		init();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent != null && intent.getAction() != null) {
			String action = intent.getAction();
			if (DlnaService.SEARCH_DEVICES.equals(action)) {
				startEngine();
			} else if (DlnaService.RESET_SEARCH_DEVICES.equals(action)) {
				restartEngine();
			}
		} else {
			log.e("intent = " + intent);
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		log.e("DlnaService onDestroy");
		unInit();
		super.onDestroy();
	}

	private void init() {
		mAllShareProxy = AllShareProxy.getInstance(this);

		mControlPoint = new ControlPoint();

		// 注册监听，当设备发生变化时通知ControlPoint
		mControlPoint.addDeviceChangeListener(this);
		mControlPoint.addSearchResponseListener(new SearchResponseListener() {
			public void deviceSearchResponseReceived(SSDPPacket ssdpPacket) {
			}
		});

		mCenterWorkThread = new ControlCenterWorkThread(this, mControlPoint);
		mCenterWorkThread.setSearchListener(this);

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case NETWORK_CHANGE:
					mAllShareProxy.resetSearch();
					break;
				}
			}

		};

		registerNetworkStatusBR();
	}

	private void unInit() {
		unRegisterNetworkStatusBR();
		mCenterWorkThread.setSearchListener(null);
		mCenterWorkThread.exit();
	}

	@Override
	public boolean startEngine() {
		awakeWorkThread();
		return true;
	}

	@Override
	public boolean stopEngine() {
		exitWorkThread();
		return true;
	}

	@Override
	public boolean restartEngine() {
		mCenterWorkThread.reset();
		return true;
	}

	@Override
	public void deviceAdded(Device dev) {
		mAllShareProxy.addDevice(dev);
	}

	@Override
	public void deviceRemoved(Device dev) {
		log.e("deviceRemoved dev = " + dev.getUDN());
		mAllShareProxy.removeDevice(dev);
	}

	private void awakeWorkThread() {
		// Thread.isAlive()方法，返回true代表Thread的run()方法还在运行，返回false表示run()方法已经运行完毕
		if (mCenterWorkThread.isAlive()) {
			mCenterWorkThread.awakeThread();
		} else {
			mCenterWorkThread.start();
		}
	}

	private void exitWorkThread() {
		if (mCenterWorkThread != null && mCenterWorkThread.isAlive()) {
			mCenterWorkThread.exit();
			long time1 = System.currentTimeMillis();
			while (mCenterWorkThread.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			long time2 = System.currentTimeMillis();
			log.e("exitCenterWorkThread cost time:" + (time2 - time1));
			mCenterWorkThread = null;
		}
	}

	@Override
	public void onSearchComplete(boolean searchSuccess) {

		if (!searchSuccess) {
			sendSearchDeviceFailBrocast(this);
		}
	}

	public static final String SEARCH_DEVICES_FAIL = "com.geniusgithub.allshare.search_devices_fail";

	// 搜索完成
	public static void sendSearchDeviceFailBrocast(Context context) {
		log.e("sendSearchDeviceFailBrocast");
		Intent intent = new Intent(SEARCH_DEVICES_FAIL);
		context.sendBroadcast(intent);
	}

	private class NetworkStatusChangeBR extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent != null) {
				String action = intent.getAction();
				if (action != null) {
					if (action
							.equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
						sendNetworkChangeMessage();
					}
				}
			}

		}

	}

	private void registerNetworkStatusBR() {
		if (mNetworkStatusChangeBR == null) {
			mNetworkStatusChangeBR = new NetworkStatusChangeBR();
			registerReceiver(mNetworkStatusChangeBR, new IntentFilter(
					ConnectivityManager.CONNECTIVITY_ACTION));
		}
	}

	private void unRegisterNetworkStatusBR() {
		if (mNetworkStatusChangeBR != null) {
			unregisterReceiver(mNetworkStatusChangeBR);
		}
	}

	private void sendNetworkChangeMessage() {
		if (firstReceiveNetworkChangeBR) {
			log.e("first receive the NetworkChangeMessage, so drop it...");
			firstReceiveNetworkChangeBR = false;
			return;
		}

		mHandler.removeMessages(NETWORK_CHANGE);
		mHandler.sendEmptyMessageDelayed(NETWORK_CHANGE, 500);
	}

}
