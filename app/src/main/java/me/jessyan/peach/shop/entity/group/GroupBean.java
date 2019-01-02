package me.jessyan.peach.shop.entity.group;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Create by HuiRan on 2019/1/1 下午1:40
 * email: 15260828327@163.com
 * description:
 */
public class GroupBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{
        private String title;
        @SerializedName("click_url")
        private String clickUrl;
        @SerializedName("orig_price")
        private String originalPrice;
        @SerializedName("jdd_price")
        private String groupPrice;
        @SerializedName("jdd_num")
        private String groupNum;
        @SerializedName("pict_url")
        private String imgUrl;
        @SerializedName("ostime")
        private String startTime;
        @SerializedName("oetime")
        private String endTime;
        @SerializedName("item_id")
        private String itemId;
        @SerializedName("user_type")
        private String shopType;

        public String getShopType() {
            return shopType;
        }

        public void setShopType(String shopType) {
            this.shopType = shopType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getGroupPrice() {
            return groupPrice;
        }

        public void setGroupPrice(String groupPrice) {
            this.groupPrice = groupPrice;
        }

        public String getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
    }
}
