package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.content.Context;
import android.util.Log;

import com.example.lj.ljplayerhd.main.offline.dlna.utils.CommonUtil;
import com.github.mediaserver.server.jni.DMSJniInterface;

public class DMSWorkThread extends Thread implements IBaseEngine {

	private static final int CHECK_INTERVAL = 30 * 1000;
	private Context mContext;
	private boolean mStartSuccess = false;
	private boolean mExitFlag = false;

	private String mRootdir = "";
	private String mFriendName = "";
	private String mUUID = "";

	public DMSWorkThread(Context context) {
		this.mContext = context;
	}

	public void setFlag(boolean flag) {
		this.mStartSuccess = flag;
	}

	public void setParam(String rootDir, String friendName, String uuid) {
		this.mRootdir = rootDir;
		this.mFriendName = friendName;
		this.mUUID = uuid;
	}
	
	public void awakeThread(){
		synchronized (this) {
			notifyAll();
		}
	}
	
	public void exit(){
		mExitFlag=true;
		awakeThread();
	}

	@Override
	public boolean startEngine() {
		if (mFriendName.length() == 0) {
			return false;
		}
		int ret = DMSJniInterface.startServer(mRootdir, mFriendName, mUUID);
		boolean result = (ret == 0 ? true : false);
		Log.e("info", "startServer.......................:"+result);
		return result;
	}

	@Override
	public boolean stopEngine() {
		DMSJniInterface.stopServer();
		return true;
	}

	@Override
	public boolean restartEngine() {
		return true;
	}

	@Override
	public void run() {
		while (true) {
			if (mExitFlag) {
				stopEngine();
				break;
			}
			refreshNotify();
			synchronized (this) {
				try {
					wait(CHECK_INTERVAL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (mExitFlag) {
				stopEngine();
				break;
			}
		}
	}

	private void refreshNotify() {
		if (!CommonUtil.checkNetworkState(mContext)){
			return ;
		}
		
		if (!mStartSuccess){
			stopEngine();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean ret = startEngine();
			if (ret){
				mStartSuccess = true;
			}
		}
	}
}
