package com.example.lj.ljplayerhd.main.offline.download.model;

import com.example.lj.ljplayerhd.bean.SearchBean;

/**
 * Created by Administrator on 2016/12/13 0013.
 * 下载任务Bean
 */

public class DownLoadTask {

    public static final int DOWNLOCADBEAN_STATUS_NOMAL = -1;
    public static final int DOWNLOCADBEAN_STATUS_START = 0;
    public static final int DOWNLOCADBEAN_STATUS_PROGRESS = 1;
    public static final int DOWNLOCADBEAN_STATUS_FINISH = 2;
    public static final int DOWNLOCADBEAN_STATUS_CANCEL = 3;
    public static final int DOWNLOCADBEAN_STATUS_ERROR = 4;
    public static final int DOWNLOCADBEAN_STATUS_WARIT = 5;


    private int id;
    private String address;
    private String fileFolder;
    private String filename;
    private String message;
    private int status = DOWNLOCADBEAN_STATUS_NOMAL;
    private int downloadProgress = 0;
    private long allCount;
    private long boforLength;
    private long fileCount;
    private String filePath;
    private String picaddr;
    private String tineLen;


    public DownLoadTask() {
    }


    public String getFileFolder() {
        return fileFolder;
    }

    public void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileCount() {
        return fileCount;
    }

    public void setFileCount(long fileCount) {
        this.fileCount = fileCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return filename;
    }

    public void setName(String name) {
        this.filename = name;
    }

    public String getPicaddr() {
        return picaddr;
    }

    public void setPicaddr(String picaddr) {
        this.picaddr = picaddr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTineLen() {
        return tineLen;
    }

    public void setTineLen(String tineLen) {
        this.tineLen = tineLen;
    }

    public long getAllCount() {
        return allCount;
    }

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }

    public long getBoforLength() {
        return boforLength;
    }

    public void setBoforLength(long boforLength) {
        this.boforLength = boforLength;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDownloadProgress() {
        return downloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DownLoadTask{" +
                "id=" + id +
                ", fileFolder='" + fileFolder + '\'' +
                ", filename='" + filename + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", downloadProgress=" + downloadProgress +
                ", allCount=" + allCount +
                ", boforLength=" + boforLength +
                ", fileCount=" + fileCount +
                ", filePath='" + filePath + '\'' +
                ", picaddr='" + picaddr + '\'' +
                ", address='" + address + '\'' +
                ", tineLen=" + tineLen +
                '}';
    }
}
