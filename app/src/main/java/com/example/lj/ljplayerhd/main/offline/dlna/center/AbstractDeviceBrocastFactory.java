package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.content.Context;


import com.example.lj.ljplayerhd.main.offline.dlna.proxy.IDeviceChangeListener;

import org.cybergarage.util.CommonLog;
import org.cybergarage.util.LogFactory;

public abstract class AbstractDeviceBrocastFactory {

	protected static final CommonLog log = LogFactory.createLog();
	
	protected Context mContext;
	protected AbstractDeviceChangeBrocastReceiver mReceiver;
	
	public AbstractDeviceBrocastFactory(Context context){
		mContext = context;
	}
	
	public abstract void registerListener(IDeviceChangeListener listener);
	public abstract void unRegisterListener();
	
}
