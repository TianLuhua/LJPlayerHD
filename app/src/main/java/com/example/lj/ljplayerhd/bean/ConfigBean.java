package com.example.lj.ljplayerhd.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wh on 2016/11/25.
 */

public class ConfigBean {

    /**
     * sid : adPTEB
     * server : http://cloud.data.3dv.cn
     * maintain :
     * thumbPath : http://img.3dv.cn/
     * subtitlePath : http://rest.3dv.cn/data/subtitle/
     * forceUpdate : 1000
     * onlinecheck : 300
     * type : [{"mark":"video","name":"电影"},{"mark":"trailers","name":"片花"},{"mark":"short","name":"短片"},{"mark":"demo","name":"演示"}]
     */
    private String sid;
    private String server;
    private String maintain;
    private String thumbPath;
    private String subtitlePath;
    private String forceUpdate;
    private String onlinecheck;
    private List<TypeBean> type;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getSubtitlePath() {
        return subtitlePath;
    }

    public void setSubtitlePath(String subtitlePath) {
        this.subtitlePath = subtitlePath;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getOnlinecheck() {
        return onlinecheck;
    }

    public void setOnlinecheck(String onlinecheck) {
        this.onlinecheck = onlinecheck;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class TypeBean{
        /**
         * mark : video
         * name : 电影
         */

        private String mark;
        private String name;

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
    }
}
