package com.example.lj.ljplayerhd.main.offline.dlna.utils;

import android.util.Log;


import com.example.lj.ljplayerhd.main.offline.dlna.model.MediaItem;

import org.cybergarage.upnp.Device;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class UpnpUtil {

	public static boolean isMediaServerDevice(Device device) {
		if ("urn:schemas-upnp-org:device:MediaServer:1".equalsIgnoreCase(device
				.getDeviceType())) {
			return true;
		}
		return false;
	}

	public static boolean isValidRenderDevice(Device device) {
		if (UpnpUtil.isMediaRenderDevice(device)
				&& !UpnpUtil.isLocalIpAddress(device)) {
			return true;
		}
		return false;
	}

	public static boolean isMediaRenderDevice(Device device) {
		if ("urn:schemas-upnp-org:device:MediaRenderer:1"
				.equalsIgnoreCase(device.getDeviceType())) {
			return true;
		}
		return false;
	}

	public final static String DLNA_OBJECTCLASS_MUSICID = "audio";
	public final static String DLNA_OBJECTCLASS_VIDEOID = "video";
	public final static String DLNA_OBJECTCLASS_PHOTOID = "image";
	public final static String DLNA_OBJECTCLASS_FOLDER = "object.container";

	public static boolean isAudioItem(MediaItem item) {
		String objectClass = item.getObjectClass();
		if (objectClass != null
				&& objectClass.contains(DLNA_OBJECTCLASS_MUSICID)) {
			return true;
		}
		return false;
	}

	public static boolean isVideoItem(MediaItem item) {
		String objectClass = item.getObjectClass();
		if (objectClass != null
				&& objectClass.contains(DLNA_OBJECTCLASS_VIDEOID)) {
			return true;
		}
		return false;
	}

	public static boolean isPictureItem(MediaItem item) {
		String objectClass = item.getObjectClass();
		if (objectClass != null
				&& objectClass.contains(DLNA_OBJECTCLASS_PHOTOID)) {
			return true;
		}
		return false;
	}

	public static boolean isStorageFolder(MediaItem item) {
		String objectClass = item.getObjectClass();
		Log.e("info", "objectClass:" + objectClass);
		if (objectClass != null) {
			if (objectClass.contains(DLNA_OBJECTCLASS_FOLDER)) {
				Log.e("info", "objectClass:" + true);
				return true;
			}
		}
		return false;
	}

	public static boolean isLocalIpAddress(Device device) {
		try {
			String addrip = device.getLocation();
			addrip = addrip.substring("http://".length(), addrip.length());
			addrip = addrip.substring(0, addrip.indexOf(":"));
			boolean ret = isLocalIpAddress(addrip);
			ret = false;
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isLocalIpAddress(String checkip) {

		boolean ret = false;
		if (checkip != null) {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf
							.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							String ip = inetAddress.getHostAddress().toString();

							if (ip == null) {
								continue;
							}
							if (checkip.equals(ip)) {
								return true;
							}
						}
					}
				}
			} catch (SocketException ex) {
				ex.printStackTrace();
			}
		}

		return ret;
	}
}
