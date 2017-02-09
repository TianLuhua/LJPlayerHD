package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.content.Context;

import org.cybergarage.upnp.Device;

import java.util.ArrayList;
import java.util.List;

public class DLNAContainer {
	private List<Device> mDevices;
	private Device mSelectedDevice;
	private Context mContext;

	public DLNAContainer(Context context) {
		this.mContext=context;
		mDevices = new ArrayList<Device>();
	}

	public synchronized void addDevice(Device d) {
		int size = mDevices.size();
		for (int i = 0; i < size; i++) {
			String udnString = mDevices.get(i).getUDN();
			if (d.getUDN().equalsIgnoreCase(udnString)) {
				return;
			}
		}
		mDevices.add(d);
		DMSDeviceBrocastFactory.sendAddBrocast(mContext);
	}

	public synchronized void removeDevice(Device d) {
		int size = mDevices.size();
		for (int i = 0; i < size; i++) {
			String udnString = mDevices.get(i).getUDN();
			if (d.getUDN().equalsIgnoreCase(udnString)) {
				Device device = mDevices.remove(i);
				boolean ret = false;
				if (mSelectedDevice != null) {
					ret = mSelectedDevice.getUDN().equalsIgnoreCase(
							device.getUDN());
				}
				if (ret) {
					mSelectedDevice = null;
				}
				DMSDeviceBrocastFactory.sendRemoveBrocast(mContext, ret);
				break;
			}
		}
	}

	public synchronized void clear() {
		if (mDevices != null) {
			mDevices.clear();
			mSelectedDevice = null;
			DMSDeviceBrocastFactory.sendClearBrocast(mContext);
		}
	}

	public Device getSelectedDevice() {
		return mSelectedDevice;
	}

	public void setSelectedDevice(Device mSelectedDevice) {
		this.mSelectedDevice = mSelectedDevice;
	}

	public List<Device> getDevices() {
		return mDevices;
	}

	public interface DeviceChangeListener {
		void onDeviceChange(Device device);
	}

}
