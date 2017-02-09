package com.example.lj.ljplayerhd.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class SLogUtil {

    public static boolean isDebug = false;
    public static final String TAG = "SLogUtil";

    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }
}
