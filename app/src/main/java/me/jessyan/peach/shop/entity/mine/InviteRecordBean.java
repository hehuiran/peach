package me.jessyan.peach.shop.entity.mine;

import java.util.List;

/**
 * @author: Created by HuiRan on 2018/1/22 11:09
 * @E-Mail: 15260828327@163.com
 * @description:
 */

public class InviteRecordBean {

    private List<DataModel> data;

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    public static class DataModel{
        private long addTime;
        private String headimgurl;
        private String label;
        private String nickname;

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
