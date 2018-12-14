package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;
import me.jessyan.peach.shop.utils.GoodsUtils;

/**
 * author: Created by HuiRan on 2018/5/7 18:47
 * E-Mail: 15260828327@163.com
 * description:
 */

public class CollectionBean implements Serializable{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends MultiItemBean implements Cloneable ,Serializable {

        @SerializedName("item_id")
        private String itemId;
        @SerializedName("item_pic")
        private String image;
        @SerializedName("item_title")
        private String title;
        @SerializedName("item_endprice")
        private String itemendprice;
        @SerializedName("coupon_money")
        private String couponmoney;
        @SerializedName("item_price")
        private String itemprice;
        @SerializedName("item_sale")
        private String itemsale;
        @SerializedName("shop_type")
        private String shoptype;
        @SerializedName("seller_nick")
        private String sellernick;
        @SerializedName("seller_id")
        private String sellerId;
        @SerializedName("couponend_time")
        private String couponendtime;
        @SerializedName("couponstart_time")
        private String couponStartTime;
        @SerializedName("currenttime")
        private String currenttime;
        private long subTime;
        private boolean isCollection;

        public String getCouponStartTime() {
            return couponStartTime;
        }

        public void setCouponStartTime(String couponStartTime) {
            this.couponStartTime = couponStartTime;
        }

        public boolean isCollection() {
            return isCollection;
        }

        public void setCollection(boolean collection) {
            isCollection = collection;
        }

        public long getSubTime() {
            return subTime;
        }

        public void setSubTime(long subTime) {
            this.subTime = subTime;
        }

        public String getCurrenttime() {
            return currenttime;
        }

        public void setCurrenttime(String currenttime) {
            this.currenttime = currenttime;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getImage() {
            return GoodsUtils.getSpecifiedSizeImageUrl(image, 400);
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getSellernick() {
            return sellernick;
        }

        public void setSellernick(String sellernick) {
            this.sellernick = sellernick;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getCouponendtime() {
            return couponendtime;
        }

        public void setCouponendtime(String couponendtime) {
            this.couponendtime = couponendtime;
        }

        @Override
        public DataBean clone() {
            try {
                return (DataBean) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
