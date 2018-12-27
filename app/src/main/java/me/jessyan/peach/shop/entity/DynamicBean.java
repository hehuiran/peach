package me.jessyan.peach.shop.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yuwenchao on 2018/11/15.
 */

public class DynamicBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends MultiItemBean {
        /**
         * author : äº’åˆ©è”ç›Ÿ
         * head_img :
         * small_images : ["https://img.alicdn.com/bao/uploaded/i4/2145616690/O1CN011zI49ZnlWx0qsbr_!!0-item_pic.jpg","https://img.alicdn.com/i3/2145616690/O1CN011zI49NuQwkajf73_!!2145616690.jpg","https://img.alicdn.com/i4/2145616690/O1CN011zI49NvWXu8s70N_!!2145616690.jpg","https://img.alicdn.com/i2/2145616690/O1CN011zI49WTLJ2u3w6K_!!2145616690.jpg","https://img.alicdn.com/i4/2145616690/O1CN011zI49GznYueneaR_!!2145616690.jpg"]
         * id : 16
         * item_id : 572999514605
         * content : åº·å¤«æ¯›è¡£æœèµ·çƒä¿®å‰ªå™¨å……ç”µå¼åŽ»æ¯›çƒç¥žå™¨é™¤æ¯›åŽ»é™¤å‰ƒæ‰“è„±æ¯›æœºå™¨å®¶ç”¨ðŸ‘®
         * comment : ðŸ˜„ðŸ˜„ðŸ˜„åº·å¤«æ¯›è¡£æœèµ·çƒä¿®å‰ªå™¨å……ç”µå¼åŽ»æ¯›çƒç¥žå™¨é™¤æ¯›åŽ»é™¤å‰ƒæ‰“è„±æ¯›æœºå™¨å®¶ç”¨
         * add_time : 1542038400
         * share_num : 0
         * item_title : åº·å¤«æ¯›è¡£æœèµ·çƒä¿®å‰ªå™¨å……ç”µå¼åŽ»æ¯›çƒç¥žå™¨é™¤æ¯›åŽ»é™¤å‰ƒæ‰“è„±æ¯›æœºå™¨å®¶ç”¨
         * item_price : 24.9
         * item_endprice : 14.9
         * coupon_money : 10
         * couponstart_time : 1541520000
         * couponend_time : 1541692800
         * tk_money : 0.80
         */

        private String author;
        @SerializedName("head_img")
        private String headImg;
        private int id;
        @SerializedName("item_id")
        private String itemId;
        private String content;
        private String comment;
        @SerializedName("add_time")
        private String addTime;
        @SerializedName("share_num")
        private int shareNum;
        @SerializedName("item_title")
        private String title;
        @SerializedName("item_price")
        private String originalPrice;
        @SerializedName("item_endprice")
        private String discountPrice;
        @SerializedName("coupon_money")
        private String couponMoney;
        @SerializedName("couponstart_time")
        private String couponStartTime;
        @SerializedName("couponend_time")
        private String couponEndTime;
        @SerializedName("tk_money")
        private String tkMoney;
        @SerializedName("small_images")
        private List<String> smallImages;
        @SerializedName("is_long_img")
        private int isLongImg;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getCouponStartTime() {
            return couponStartTime;
        }

        public void setCouponStartTime(String couponStartTime) {
            this.couponStartTime = couponStartTime;
        }

        public String getCouponEndTime() {
            return couponEndTime;
        }

        public void setCouponEndTime(String couponEndTime) {
            this.couponEndTime = couponEndTime;
        }

        public String getTkMoney() {
            return tkMoney;
        }

        public void setTkMoney(String tkMoney) {
            this.tkMoney = tkMoney;
        }

        public List<String> getSmallImages() {
            return smallImages;
        }

        public void setSmallImages(List<String> smallImages) {
            this.smallImages = smallImages;
        }

        public int getIsLongImg() {
            return isLongImg;
        }

        public void setIsLongImg(int isLongImg) {
            this.isLongImg = isLongImg;
        }
    }
}
