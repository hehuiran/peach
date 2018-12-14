package me.jessyan.peach.shop.entity.goods;

import java.util.List;

/**
 * Created by yuwenchao on 2018/11/20.
 */

public class SearchBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * itemId : 580120566076
         * title : 羽绒服男士2018新款冬季新款白鸭绒加厚中长款学生韩版潮流外套A
         * itemendprice : 129.0
         * image : http://img.alicdn.com/imgextra/i4/2129184735/O1CN011kqfq7FTwuuTGMs_!!2129184735.jpg
         * couponmoney : 570
         * itemprice : 699.0
         * itemsale : 427
         * commissionMoney : 570
         * shoptype : B
         * tkmoney : 0
         * sellerId :
         * sellernick : 华欧铭仕旗舰店
         * couponendtime : 1542988799
         * couponstarttime : 1542643200
         * tkrates : 30.0
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
        private String tkrates;

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

        public String getTkrates() {
            return tkrates;
        }

        public void setTkrates(String tkrates) {
            this.tkrates = tkrates;
        }
    }
}
