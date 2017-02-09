package com.example.lj.ljplayerhd.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wh on 2016/12/29.
 */

public class LiveBean {

    /**
     * dm_error : 0
     * error_msg : 操作成功
     * expire_time : 60
     * lives : [{"creator":{"id":321832630,"level":1,"gender":1,"nick":"陈博洋","portrait":"http://wx
     */

    private int dm_error;
    private String error_msg;
    private int expire_time;
    @SerializedName("lives")
    private List<RoomBean> rooms;

    public int getDm_error() {
        return dm_error;
    }

    public void setDm_error(int dm_error) {
        this.dm_error = dm_error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public List<RoomBean> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomBean> rooms) {
        this.rooms = rooms;
    }

    public static class RoomBean {
        /**
         * creator :
         * id : 1483013185701956
         * name :
         * city : 北京市
         * share_addr :
         * stream_addr : http://pull5.a8.com/live/1483013185701956.flv
         * version : 0
         * slot : 5
         * optimal : 0
         * group : 60
         * distance : 3.7km
         * link : 0
         * multi : 0
         * rotate : 0
         * tag_id : 1
         */

        private CreatorBean creator;
        private String id;
        private String name;
        private String city;
        private String share_addr;
        private String stream_addr;
        private int version;
        private int slot;
        private int optimal;
        private int group;
        private String distance;
        private int link;
        private int multi;
        private int rotate;
        private int tag_id;

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city==null?"未知位置":city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getShare_addr() {
            return share_addr;
        }

        public void setShare_addr(String share_addr) {
            this.share_addr = share_addr;
        }

        public String getStream_addr() {
            return stream_addr;
        }

        public void setStream_addr(String stream_addr) {
            this.stream_addr = stream_addr;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public int getOptimal() {
            return optimal;
        }

        public void setOptimal(int optimal) {
            this.optimal = optimal;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getLink() {
            return link;
        }

        public void setLink(int link) {
            this.link = link;
        }

        public int getMulti() {
            return multi;
        }

        public void setMulti(int multi) {
            this.multi = multi;
        }

        public int getRotate() {
            return rotate;
        }

        public void setRotate(int rotate) {
            this.rotate = rotate;
        }

        public int getTag_id() {
            return tag_id;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        @Override
        public String toString() {
            return getCity()+" "+getCreator().getNick();
        }

        public static class CreatorBean {
            /**
             * id : 321832630
             * level : 1
             * gender : 1
             * nick : 陈博洋
             * portrait :
             */

            private int id;
            private int level;
            private int gender;
            private String nick;
            private String portrait;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getNick() {
                return nick==null?"无名":nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }
        }
    }
}
