package com.example.lj.ljplayerhd.bean;

import com.example.lj.ljplayerhd.config.IDatas;
import com.google.gson.annotations.SerializedName;
import com.oguzdev.circularfloatingactionmenu.library.animation.MenuAnimationHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wh on 2016/12/26.
 */

public class CategoryModel {

    /**
     * category : {"index":"0","label":"按分类","item":[{"id":"0","text":"全部"},{"id":"1","text":"欧美大片"},{"id":"2","text":"日韩剧场"},{"id":"3","text":"华语大片"},{"id":"5","text":"动漫频道"},{"id":"4","text":"其它"}]}
     * class : {"index":"1","label":"按类型","item":[{"id":"0","text":"全部"},{"id":"1","text":"动作"},{"id":"2","text":"喜剧"},{"id":"3","text":"爱情"},{"id":"4","text":"科幻"},{"id":"5","text":"灾难"},{"id":"6","text":"恐怖"},{"id":"7","text":"悬疑"},{"id":"8","text":"魔幻"},{"id":"9","text":"战争"},{"id":"10","text":"罪案"},{"id":"11","text":"惊悚"},{"id":"12","text":"动画"},{"id":"13","text":"伦理"},{"id":"14","text":"纪录"},{"id":"15","text":"剧情"}]}
     * time : {"index":"2","label":"按时间","item":[{"id":"0","text":"全部"},{"id":"1","text":"2011-至今"},{"id":"2","text":"2006-2010"},{"id":"3","text":"2001-2005"},{"id":"4","text":"1996-2000"},{"id":"5","text":"1990-1995"},{"id":"6","text":"更早"}]}
     */

    private CategoryBean category;
    @SerializedName("class")
    private ClassBean clazz;
    private TimeBean time;

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public ClassBean getClazz() {
        return clazz;
    }

    public void setClazz(ClassBean classX) {
        this.clazz = classX;
    }

    public TimeBean getTime() {
        return time;
    }

    public void setTime(TimeBean time) {
        this.time = time;
    }

    public Map<String, Object> getCategorys() {
        Map<String, Object> map = new HashMap<>();
        map.put(IDatas.JsonKey.CATEGORY, category);
        map.put(IDatas.JsonKey.CLAZZ, clazz);
        map.put(IDatas.JsonKey.TIME, time);
        return map;
    }

    public List<String> getCategoryKeys() {
        List<String> keys=new ArrayList<>();
        keys.add(IDatas.JsonKey.CATEGORY);
        keys.add(IDatas.JsonKey.CLAZZ);
        keys.add(IDatas.JsonKey.TIME);
        return keys;
    }

    public String[] getCategoryLabels() {
        String[] Labels = new String[3];
        Labels[0] = category == null ? "" : category.getLabel();
        Labels[1] = clazz == null ? "" : clazz.getLabel();
        Labels[2] = time == null ? "" : time.getLabel();
        return Labels;
    }

    public static class CategoryBean {
        /**
         * index : 0
         * label : 按分类
         * item : [{"id":"0","text":"全部"},{"id":"1","text":"欧美大片"},{"id":"2","text":"日韩剧场"},{"id":"3","text":"华语大片"},{"id":"5","text":"动漫频道"},{"id":"4","text":"其它"}]
         */

        private String index;
        private String label;
        private List<ItemBean> item;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * id : 0
             * text : 全部
             */

            private String id;
            private String text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }

    public static class ClassBean {
        /**
         * index : 1
         * label : 按类型
         * item : [{"id":"0","text":"全部"},{"id":"1","text":"动作"},{"id":"2","text":"喜剧"},{"id":"3","text":"爱情"},{"id":"4","text":"科幻"},{"id":"5","text":"灾难"},{"id":"6","text":"恐怖"},{"id":"7","text":"悬疑"},{"id":"8","text":"魔幻"},{"id":"9","text":"战争"},{"id":"10","text":"罪案"},{"id":"11","text":"惊悚"},{"id":"12","text":"动画"},{"id":"13","text":"伦理"},{"id":"14","text":"纪录"},{"id":"15","text":"剧情"}]
         */

        private String index;
        private String label;
        private List<ItemBean> item;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * id : 0
             * text : 全部
             */

            private String id;
            private String text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }

    public static class TimeBean {
        /**
         * index : 2
         * label : 按时间
         * item : [{"id":"0","text":"全部"},{"id":"1","text":"2011-至今"},{"id":"2","text":"2006-2010"},{"id":"3","text":"2001-2005"},{"id":"4","text":"1996-2000"},{"id":"5","text":"1990-1995"},{"id":"6","text":"更早"}]
         */

        private String index;
        private String label;
        private List<ItemBean> item;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * id : 0
             * text : 全部
             */

            private String id;
            private String text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
