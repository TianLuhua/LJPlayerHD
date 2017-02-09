package com.example.lj.ljplayerhd.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by andy.shangguan on 2016/11/23.
 */

public class LocalVedio {
    private int id;
    private String thumbPath;
    private String path;
    private String title;
    private String displayName;
    private String mimeType;
    private String size;

    public LocalVedio() {

    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return "LocalVedio{" +
                "id=" + id +
                ", thumbPath='" + thumbPath + '\'' +
                ", path='" + path + '\'' +
                ", title='" + title + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


}
