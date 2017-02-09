package com.example.lj.ljplayerhd.bean;

import com.bumptech.glide.Glide;

/**
 * Created by wh on 2016/11/29.
 */

public class SearchBean {

    /**
     * id : 207
     * mark : video
     * name : 新天师斗僵尸
     * picaddr : day_120412/F/density_55C6DD.jpg
     * year : 2011
     * director : 克雷格·吉勒斯佩
     * playactor : 安东·尤金,伊莫琴·普茨,柯林·法瑞尔,克里斯托夫·梅兹-普莱瑟
     * area : 欧美
     * type : 喜剧 恐怖
     * track : en
     * caption : 5C74A94B.srt
     * captionen :
     * timelen : 6364
     * address : /vod/207/207_480PH.mp4|/vod/207/207_480P.mp4|NULL
     * score : 8.0
     * snum : 0
     */
    private String id;
    private String mark;
    private String name;
    private String picaddr;
    private String year;
    private String director;
    private String playactor;
    private String area;
    private String type;
    private String track;
    private String caption;
    private String captionen;
    private String timelen;
    private String address;
    private String score;
    private String snum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicaddr() {
        return picaddr;
    }

    public void setPicaddr(String picaddr) {
        this.picaddr = picaddr;
    }

    public String getYear() {
        return "(" + (year == null ? "" : year) + ")";
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return "导演: " + (director == null ? "" : director) + "\n";
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlayactor() {
        return "主演: " + (playactor == null ? "" : playactor) + "\n";
    }

    public void setPlayactor(String playactor) {
        this.playactor = playactor;
    }

    public String getArea() {
        return "地区: " + (area == null ? "" : area) + "\n";
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return "类型: " + (type == null ? "" : type) + "\n";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrack() {
        return "音轨: " + (track == null ? "" : track) + "\n";
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaptionen() {
        return "字幕: " + (captionen == null ? "" : captionen) + "\n";
    }

    public void setCaptionen(String captionen) {
        this.captionen = captionen;
    }

    public String getTimelen() {
        return "片长: " + (timelen == null ? "" : timelen) + "\n";
    }

    public void setTimelen(String timelen) {
        this.timelen = timelen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }

    @Override
    public String toString() {
        return getDirector()
                + getPlayactor()
                + getType()
                + getArea()
                + getTrack()
                + getCaptionen()
                + getTimelen();
    }
}
