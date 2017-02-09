package com.example.lj.ljplayerhd.bean;

import java.util.List;

/**
 * Created by wh on 2016/12/28.
 */

public class CommentBean {

    /**
     * splitpage : {"total":"10","pagesize":"20","current":"1"}
     * pagecontent : [{"listid":"10","model":"IM-A890S","uid":"3062174","username":"18620813900","content":"不错","dateline":"2014-09-08 13:21:47"},{"listid":"9","model":"GT-I9152","uid":"3051726","username":"hong100759","content":"我帶三d眼鏡睇點解無三d效果嘅。","dateline":"2014-08-25 02:48:59"},{"listid":"8","model":"vivo Y13","uid":"3044804","username":"7896003640","content":"我带眼镜看怎么没有3d效果啊","dateline":"2014-08-16 19:58:14"},{"listid":"7","model":"super touch","uid":"0","username":"super touch","content":"فلباغفلببلبيلرتخ","dateline":"2014-08-12 07:01:58"},{"listid":"6","model":"super touch","uid":"0","username":"super touch","content":"٪٪٤٤٥٨","dateline":"2014-08-12 07:01:31"},{"listid":"5","model":"Coolpad 7296","uid":"0","username":"Coolpad 7296","content":"我要号","dateline":"2014-08-09 03:24:00"},{"listid":"4","model":"Coolpad 7296","uid":"0","username":"Coolpad 7296","content":"我要号","dateline":"2014-08-09 03:24:00"},{"listid":"3","model":"Coolpad 7296","uid":"0","username":"Coolpad 7296","content":"我要号","dateline":"2014-08-09 03:23:59"},{"listid":"2","model":"HTC EVO 3D X515m","uid":"2645733","username":"4106994885","content":"3d效果很好,值得一看!","dateline":"2014-08-07 23:10:22"},{"listid":"1","model":"HTC EVO 3D X515m","uid":"680362","username":"jpnaruto","content":"好看","dateline":"2014-08-06 23:50:15"}]
     */

    private SplitpageBean splitpage;
    private List<PagecontentBean> pagecontent;

    public SplitpageBean getSplitpage() {
        return splitpage;
    }

    public void setSplitpage(SplitpageBean splitpage) {
        this.splitpage = splitpage;
    }

    public List<PagecontentBean> getPagecontent() {
        return pagecontent;
    }

    public void setPagecontent(List<PagecontentBean> pagecontent) {
        this.pagecontent = pagecontent;
    }

    public static class SplitpageBean {
        /**
         * total : 10
         * pagesize : 20
         * current : 1
         */

        private String total;
        private String pagesize;
        private String current;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPagesize() {
            return pagesize;
        }

        public void setPagesize(String pagesize) {
            this.pagesize = pagesize;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }
    }

    public static class PagecontentBean {
        /**
         * listid : 10
         * model : IM-A890S
         * uid : 3062174
         * username : 18620813900
         * content : 不错
         * dateline : 2014-09-08 13:21:47
         */

        private String listid;
        private String model;
        private String uid;
        private String username;
        private String content;
        private String dateline;

        public String getListid() {
            return listid;
        }

        public void setListid(String listid) {
            this.listid = listid;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }
    }
}
