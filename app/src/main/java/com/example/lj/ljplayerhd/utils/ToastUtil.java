package com.example.lj.ljplayerhd.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    public static boolean isShow = true;

    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }
    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow && mContext!=null)
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param messageId
     */
    public static void showShort(int messageId) {
        if (isShow && mContext!=null)
            Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow && mContext!=null)
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param messageId
     */
    public static void showLong(int messageId) {
        if (isShow && mContext!=null)
            Toast.makeText(mContext, messageId, Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @param message
     * @param gravity
     * @param offX
     * @param offY
     */
    public static void showShortGravity(CharSequence message,int gravity,int offX,int offY){
        if(isShow && mContext!=null) {
            Toast toast=Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.setGravity(gravity,offX,offY);
            toast.show();
        }
    }

    /**
     *
     * @param messageId
     * @param gravity
     * @param offX
     * @param offY
     */
    public static void showShortGravity(int messageId,int gravity,int offX,int offY){
        if(isShow && mContext!=null) {
            Toast toast=Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT);
            toast.setGravity(gravity,offX,offY);
            toast.show();
        }
    }


    public static void showLongGravity(CharSequence message,int gravity,int offX,int offY){
        if(isShow && mContext!=null) {
            Toast toast=Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.setGravity(gravity,offX,offY);
            toast.show();
        }
    }

    public static void showLongGravity(int messageId,int gravity,int offX,int offY){
        if(isShow && mContext!=null) {
            Toast toast=Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT);
            toast.setGravity(gravity,offX,offY);
            toast.show();
        }
    }
}
