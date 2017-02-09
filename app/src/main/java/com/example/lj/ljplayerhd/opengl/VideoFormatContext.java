package com.example.lj.ljplayerhd.opengl;

import android.util.SparseArray;

/**
 * Created by wh on 2017/1/14.
 */

public class VideoFormatContext {
    private static SparseArray videos=new SparseArray();
    public static final int VIDEO_DEFAULT=0x01;
    public static final int VIDEO_DOUBLE=0x02;
    public static final int VIDEO_HALF=0x03;

    public static void format_register_all(){
        DefaultVideoFormat defaultVideoFormat=new DefaultVideoFormat();
        videos.put(VIDEO_DEFAULT,defaultVideoFormat);
        DoubleVideoFormat doubleVideoFormat=new DoubleVideoFormat();
        videos.put(VIDEO_DOUBLE,doubleVideoFormat);
        HalfVideoFormat halfVideoFormat=new HalfVideoFormat();
        videos.put(VIDEO_HALF,halfVideoFormat);
    }

    public static void format_register(int type,VideoFormat format){
        if (format!=null)
            videos.put(type,format);
    }

    public static VideoFormat getVideoFormat(int type){
        return (VideoFormat) videos.get(type);
    }
}
