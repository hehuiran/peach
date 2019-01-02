package me.jessyan.peach.shop.entity.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/5/18 09:48
 * E-Mail: 15260828327@163.com
 * description:
 */

public class InviteFriendBean {

    @SerializedName("data")
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        @SerializedName("imgurl")
        private String img;
        @SerializedName("Jumpurl")
        private String shareUrl;
        private boolean checked;
        private String title;
        private String icon;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
