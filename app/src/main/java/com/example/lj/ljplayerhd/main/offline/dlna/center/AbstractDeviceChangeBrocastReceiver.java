package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.example.lj.ljplayerhd.main.offline.dlna.proxy.IDeviceChangeListener;

import org.cybergarage.util.CommonLog;
import org.cybergarage.util.LogFactory;

public abstract class AbstractDeviceChangeBrocastReceiver extends BroadcastReceiver{

	public static final CommonLog log = LogFactory.createLog();
	protected IDeviceChangeListener mListener;
	
	@Override
	public void onReceive(Context context, Intent intent) {
	
	}
	
	public void setListener(IDeviceChangeListener listener){
		mListener  = listener;
	}
	
}
