package me.jessyan.peach.shop.entity.mine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/5/15 18:17
 * E-Mail: 15260828327@163.com
 * description:
 */

public class MineInfoUserIdentityBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * usergrade : 店长
         * useridentity : 1
         */

        private String usergrade;
        private String useridentity;
        @SerializedName("inviter")
        private String subordinate;

        public String getSubordinate() {
            return subordinate;
        }

        public void setSubordinate(String subordinate) {
            this.subordinate = subordinate;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getUseridentity() {
            return useridentity;
        }

        public void setUseridentity(String useridentity) {
            this.useridentity = useridentity;
        }
    }
}
