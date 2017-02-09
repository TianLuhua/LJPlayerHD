package com.example.lj.ljplayerhd.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BuildConfig {
    public final static boolean DEBUG = true;
    public final static boolean LOG_TO_FILE = false;
    public final static boolean NO_TOUYUBE = false;
    public final static boolean LOOP_PLAYING = true;
    public final static boolean HINT_LAST_PLAY_POSITION = true;
    public final static boolean HIDE_NAGIVATION = false;


    public static boolean DISABLE_ONLINE_VIDEO() {
	return android.os.Build.MODEL.equals("N103D");
    }

    public static boolean HAS_3DV_OMP_SUPPORT() {
	return android.os.Build.MODEL.equals("h710") && false;
    }
    public static boolean HAS_WIDE_HARDWARE_DECODING_SUPPORT() {
	if (android.os.Build.MODEL.equals("P70a")
		|| android.os.Build.MODEL.equals("MID-A20")
		|| android.os.Build.MODEL.equals("k970"))
	    return true;
	BufferedReader br = null;
	FileReader fr = null;
	try {
	    fr = new FileReader("/proc/cpuinfo");
	    br = new BufferedReader(fr);
	    String text;
	    while ((text = br.readLine()) != null) {
		String[] array = text.split(":\\s+", 2);
		if (array.length < 2)
		    continue;
		boolean b1 = array[0].contains("Hardware");
		boolean b2 = array[1].matches("sun\\di");
		if (b1 && b2)
		    return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (br != null)
		    br.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    try {
		if (fr != null)
		    fr.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return false;
    }
}
