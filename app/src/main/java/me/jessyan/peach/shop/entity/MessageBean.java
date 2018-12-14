package me.jessyan.peach.shop.entity;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/5/11 11:48
 * E-Mail: 15260828327@163.com
 * description:
 */

public class MessageBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends MultiItemBean {
        /**
         * id : 1
         * type : 0
         * addTime : 1514955799000
         * title : 早起挑战正式上线
         * summary : 成为早起执行，瓜分百万早起奖励金
         * url : null
         * img :
         */

        private String id;
        private int type;
        private long addTime;
        private String title;
        private String summary;
        private String linkurl;
        private String img;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", type=" + type +
                    ", addTime=" + addTime +
                    ", title='" + title + '\'' +
                    ", summary='" + summary + '\'' +
                    ", linkurl='" + linkurl + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }


        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
