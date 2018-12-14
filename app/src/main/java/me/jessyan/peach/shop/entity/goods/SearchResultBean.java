package me.jessyan.peach.shop.entity.goods;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/5/23 11:21
 * E-Mail: 15260828327@163.com
 * description:
 */

public class SearchResultBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * itemId : 565357270369
         * title : 夏季男士圆领运动t恤潮男装韩版半袖大码青少年学生宽松纯棉短袖
         * itemendprice : 9.8
         * image : https://img.alicdn.com/imgextra/i3/2005147151/TB27gIWebsrBKNjSZFpXXcXhFXa_!!2005147151.jpg
         * couponmoney : 50
         * itemprice : 59.8
         * itemsale : 21684
         * commissionMoney : 50
         * shoptype : B
         * tkmoney : 0
         * sellerId :
         * sellernick : 千禧蚂蚁旗舰店
         * couponendtime : 1532275199
         * couponstarttime : 1531670400
         */

        private String itemId;
        private String title;
        private String itemendprice;
        private String image;
        private String couponmoney;
        private String itemprice;
        private String itemsale;
        private String commissionMoney;
        private String shoptype;
        private String tkmoney;
        private String sellerId;
        private String sellernick;
        private String couponendtime;
        private String couponstarttime;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getItemendprice() {
            return itemendprice;
        }

        public void setItemendprice(String itemendprice) {
            this.itemendprice = itemendprice;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCouponmoney() {
            return couponmoney;
        }

        public void setCouponmoney(String couponmoney) {
            this.couponmoney = couponmoney;
        }

        public String getItemprice() {
            return itemprice;
        }

        public void setItemprice(String itemprice) {
            this.itemprice = itemprice;
        }

        public String getItemsale() {
            return itemsale;
        }

        public void setItemsale(String itemsale) {
            this.itemsale = itemsale;
        }

        public String getCommissionMoney() {
            return commissionMoney;
        }

        public void setCommissionMoney(String commissionMoney) {
            this.commissionMoney = commissionMoney;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getTkmoney() {
            return tkmoney;
        }

        public void setTkmoney(String tkmoney) {
            this.tkmoney = tkmoney;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getSellernick() {
            return sellernick;
        }

        public void setSellernick(String sellernick) {
            this.sellernick = sellernick;
        }

        public String getCouponendtime() {
            return couponendtime;
        }

        public void setCouponendtime(String couponendtime) {
            this.couponendtime = couponendtime;
        }

        public String getCouponstarttime() {
            return couponstarttime;
        }

        public void setCouponstarttime(String couponstarttime) {
            this.couponstarttime = couponstarttime;
        }
    }
}
