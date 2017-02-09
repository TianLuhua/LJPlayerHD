package com.example.lj.ljplayerhd.main.offline.dlna.utils;

import android.content.Context;


import com.example.lj.ljplayerhd.main.offline.dlna.center.LocalConfigSharePreference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DlnaUtils {

	public static boolean setDevName(Context context, String friendName) {
		return LocalConfigSharePreference.commintDevName(context, friendName);
	}

	public static String getDevName(Context context) {
		return LocalConfigSharePreference.getDevName(context);
	}

	public static String creat12BitUUID(Context context, String suffix) {
		String defaultUUID = "123456789abc";

		String mac = CommonUtil.getLocalMacAddress(context);

		mac = mac.replace(":", "");
		mac = mac.replace(".", "");

		if (mac.length() != 12) {
			mac = defaultUUID;
		}
		mac += suffix;
		return mac;
	}

	public static int convertSeekRelTimeToMs(String reltime) {
		int sec = 0;
		int ms = 0;
		String[] times = reltime.split(":");
		if (3 != times.length)
			return 0;
		if (!isNumeric(times[0]))
			return 0;
		int hour = Integer.parseInt(times[0]);
		if (!isNumeric(times[1]))
			return 0;
		int min = Integer.parseInt(times[1]);
		String[] times2 = times[2].split("\\.");
		if (2 == times2.length) {// 00:00:00.000
			if (!isNumeric(times2[0]))
				return 0;
			sec = Integer.parseInt(times2[0]);
			if (!isNumeric(times2[1]))
				return 0;
			ms = Integer.parseInt(times2[1]);
		} else if (1 == times2.length) {// 00:00:00
			if (!isNumeric(times2[0]))
				return 0;
			sec = Integer.parseInt(times2[0]);
		}
		return (hour * 3600000 + min * 60000 + sec * 1000 + ms);
	}

	public static boolean isNumeric(String str) {
		if ("".equals(str))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static String formatTimeFromMSInt(int time) {
		String hour = "00";
		String min = "00";
		String sec = "00";
		String split = ":";
		int tmptime = time;
		int tmp = 0;
		if (tmptime >= 3600000) {
			tmp = tmptime / 3600000;
			hour = formatHunToStr(tmp);
			tmptime -= tmp * 3600000;
		}
		if (tmptime >= 60000) {
			tmp = tmptime / 60000;
			min = formatHunToStr(tmp);
			tmptime -= tmp * 60000;
		}
		if (tmptime >= 1000) {
			tmp = tmptime / 1000;
			sec = formatHunToStr(tmp);
			tmptime -= tmp * 1000;
		}

		String ret = hour + split + min + split + sec;
		return ret;
	}

	private static String formatHunToStr(int hun) {
		hun = hun % 100;
		if (hun > 9)
			return ("" + hun);
		else
			return ("0" + hun);
	}

	public static String formateTime(long millis) {
		String str = "";
		int hour = 0;
		int time = (int) (millis / 1000);
		int second = time % 60;
		int minute = time / 60;
		if (minute >= 60) {
			hour = minute / 60;
			minute %= 60;
			str = String.format("%02d:%02d:%02d", hour, minute, second);
		} else {
			str = String.format("%02d:%02d", minute, second);
		}

		return str;

	}
	
	public static String creatMetaData(String mediaTitle, String mediaType) {
		StringBuilder builder = new StringBuilder();
		builder.append("<DIDL-Lite xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite\"");
		builder.append("xmlns:dc=\"http://purl.org/dc/elements/1.1/\"");
		builder.append("xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\">");
		builder.append("<item id=\"0\" parentID=\"0\" restricted=\"0\">");
		builder.append("<dc:title>" + mediaTitle + "</dc:title>");
		builder.append("<dc:date>" + "Unkown" + "</dc:date>");
		builder.append("<dc:creator>" + "Unkown" + "</dc:creator>");
		builder.append("<upnp:class>" + mediaType + "</upnp:class>");
		builder.append("<upnp:genre>" + "Unkown" + "</upnp:genre>");
		builder.append("<upnp:artist>" + "Unkown" + "</upnp:artist>");
		builder.append("<upnp:album>" + "Unkown" + "</upnp:album>");
		builder.append("<upnp:albumArtURI>" + "Unkown" + "</upnp:albumArtURI>");
		builder.append("<res protocolInfo=\"file-get:*:*:*:DLNA.ORG_OP=01;DLNA.ORG_CI=0;");
		builder.append("DLNA.ORG_FLAGS=00000000001000000000000000000000\" protection=\"\" tokenType=\"0\"");
		builder.append("bitrate=\"0\" duration=\"\" size=\"%u\" colorDepth=\"0\" ifoFileURI=\"\" ");
		builder.append("resolution=\"\">%s</res></item></DIDL-Lite>");
		return builder.toString();
	}
}
