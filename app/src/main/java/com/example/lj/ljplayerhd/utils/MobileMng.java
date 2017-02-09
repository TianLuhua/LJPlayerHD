package com.example.lj.ljplayerhd.utils;

import java.io.File;
import java.io.FileFilter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.example.lj.ljplayerhd.config.IDatas;


public class MobileMng {
    /**
     * 得到机型
     *
     * @return
     */
    public static String getModel() {
	String model = android.os.Build.MODEL;
	return model;
    }

    /**
     * 得到Mac地址
     *
     * @return
     */
    public static String getMac(Context context) {
	WifiManager wifi = (WifiManager) context
		.getSystemService(Context.WIFI_SERVICE);
	WifiInfo info = wifi.getConnectionInfo();
	return info.getMacAddress();
    }

    /**
     * 得到IP地址
     *
     * @return
     */
    public String getIP() {
	try {
	    for (Enumeration<NetworkInterface> en = NetworkInterface
		    .getNetworkInterfaces(); en.hasMoreElements();) {
		NetworkInterface intf = en.nextElement();
		for (Enumeration<InetAddress> enumIpAddr = intf
			.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		    InetAddress inetAddress = enumIpAddr.nextElement();
		    if (!inetAddress.isLoopbackAddress()) {
			return inetAddress.getHostAddress().toString();
		    }
		}
	    }
	} catch (SocketException ex) {
	}
	return null;
    }

    /**
     * 得到操作系统版本
     *
     * @return
     */
    public String getOsInfo() {
	return android.os.Build.VERSION.RELEASE;
    }

    /**
     *  是否为中文系统
     *
     * @return
     */
    public static boolean  getLanguage() {
	return "zh".equals(Locale.getDefault().getLanguage());
    }

    /**
     * 获取本机手机号码
     *
     * @return
     */
    public static String getPhoneNum(Context context) {
	String phoneId = "";
	try {
	    TelephonyManager tm = (TelephonyManager) context
		    .getSystemService(Context.TELEPHONY_SERVICE);
	    phoneId = tm.getLine1Number();
	    if (phoneId == null || phoneId.equals("")) {
		phoneId = tm.getSubscriberId().substring(5,
			tm.getSubscriberId().length());
	    }
	} catch (Exception e) {// 没有SIM卡
	    phoneId = getMac(context).replace(":", "");
	}
	return phoneId;
    }

    /**
     * 获得版本名
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
	try {
	    return context.getPackageManager().getPackageInfo(
		    "com.example.lj.ljplayerhd", 0).versionName;
	} catch (NameNotFoundException e) {
	    return "";
	}
    }

    /**
     * 获得版本号
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
	try {
	    return context.getPackageManager().getPackageInfo(
		    "com.example.lj.ljplayerhd", 0).versionCode;
	} catch (Exception e) {
	    return 0;
	}
    }

    /**
     * 获得系统所有字体
     *
     * @return
     */
    public static List<String> getAllFonts() {
	try {
	    File f = new File(IDatas.DefaultValues.FONT_PATH);
	    File[] listFile = f.listFiles(new FileFilter() {

		@Override
		public boolean accept(File pathname) {
		    if (pathname.getName().endsWith(
			    IDatas.DefaultValues.FONT_END))
			return true;
		    return false;
		}
	    });
	    List<String> fontNames = new ArrayList<String>(listFile.length);
	    for (int i = 0; i < listFile.length; i++) {
		String name = listFile[i].getName();
		name = name.substring(0, name.length()
			- IDatas.DefaultValues.FONT_END.length());
		fontNames.add(i, name);
	    }
	    return fontNames;
	} catch (Exception e) {
	}
	return null;
    }
}
