package com.example.lj.ljplayerhd.main.offline.dlna.proxy;

import org.cybergarage.upnp.Device;

import java.util.List;

public interface IDeviceOperator {

	public void  addDevice(Device d);
	public void removeDevice(Device d);
	public void clearDevice();
	
	public static interface IDMSDeviceOperator{
		public  List<Device> getDMSDeviceList();
		public Device getDMSSelectedDevice();
		public void setDMSSelectedDevice(Device selectedDevice);

	}
	
}
