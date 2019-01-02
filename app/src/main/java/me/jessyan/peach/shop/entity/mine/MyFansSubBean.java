package me.jessyan.peach.shop.entity.mine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/4/18 12:12
 * E-Mail: 15260828327@163.com
 * description:
 */

public class MyFansSubBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{
        @SerializedName("headimgurl")
        private String icon;
        @SerializedName("nickname")
        private String name;
        private String mobile;
        @SerializedName("inviter_time")
        private String time;        //time 注册时间
        private String count;       //已邀请人数
        private String id;
        private String usergrade;   //会员等级
        private String topUser;     //上级名称


        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTopUser() {
            return topUser;
        }

        public void setTopUser(String topUser) {
            this.topUser = topUser;
        }
    }
}
