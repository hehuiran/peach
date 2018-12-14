package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * Created by dxp on 2018/8/3.
 * Describe :
 */

public class RecommendBean extends MultiItemBean implements Cloneable {

    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }


    public RecommendBean(List<Data> data){
        setData(data);
    }

    public RecommendBean(){}

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public RecommendBean clone() {
        //ProductDetailsRecommendModel
        try {
            RecommendBean resultsModel = (RecommendBean) super.clone();
            List<Data> list = new ArrayList<>();
            for (Data recommendModel : resultsModel.data) {
                list.add(recommendModel.clone());
            }
            resultsModel.setData(list);

            return resultsModel;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Data extends MultiItemBean implements Cloneable {

        /**
         * coupon_id : 4f651987d4b24608a6bb9ebc4207c635
         * coupon_money : 50
         * couponend_time : 1533312000
         * couponstart_time : 1532793600

         * item_id : 566288534033
         * item_pic : https://img.alicdn.com/bao/uploaded/i3/1669375249/TB2TRBxkJknBKNjSZKPXXX6OFXa_!!1669375249.jpg
         * item_price : 226
         * item_endprice : 168
         * item_sale : 1
         * item_title : 43韩版运动套装 2018年秋季新款休闲两件套女装
         * seller_id : 1669375249
         * seller_nick : 小志鸿服饰
         * shop_type : c
         * small_images : https://img.alicdn.com/i1/1669375249/TB2rLnPkRsmBKNjSZFsXXaXSVXa_!!1669375249.jpg|https://img.alicdn.com/i3/1669375249/TB2J944kRnTBKNjSZPfXXbf1XXa_!!1669375249.jpg|https://img.alicdn.com/i3/1669375249/TB2Li55f3ZC2uNjSZFnXXaxZpXa_!!1669375249.jpg|https://img.alicdn.com/i1/1669375249/TB2.fZgkScqBKNjSZFgXXX_kXXa_!!1669375249.jpg|
         */

        @SerializedName("coupon_id")
        private String coupon_id;
        @SerializedName("coupon_money")
        private String coupon_money;
        @SerializedName("couponend_time")
        private String couponend_time;
        @SerializedName("couponstart_time")
        private String couponstart_time;
        @SerializedName("item_endprice")
        private String item_endprice;       //最终价格
        @SerializedName("item_id")
        private String item_id;
        @SerializedName("item_pic")
        private String item_pic;
        @SerializedName("item_price")
        private String item_price;          //
        @SerializedName("item_sale")
        private int item_sale;
        @SerializedName("item_title")
        private String item_title;
        @SerializedName("seller_id")
        private String seller_id;
        @SerializedName("seller_nick")
        private String seller_nick;
        @SerializedName("shop_type")
        private String shop_type;
        @SerializedName("small_images")
        private String small_images;

        @Override
        public Data clone() {
            try {
                return (Data) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getCouponend_time() {
            return couponend_time;
        }

        public void setCouponend_time(String couponend_time) {
            this.couponend_time = couponend_time;
        }

        public String getCouponstart_time() {
            return couponstart_time;
        }

        public void setCouponstart_time(String couponstart_time) {
            this.couponstart_time = couponstart_time;
        }

        public String getItem_endprice() {
            return item_endprice;
        }

        public void setItem_endprice(String item_endprice) {
            this.item_endprice = item_endprice;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_pic() {
            return item_pic;
        }

        public void setItem_pic(String item_pic) {
            this.item_pic = item_pic;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public int getItem_sale() {
            return item_sale;
        }

        public void setItem_sale(int item_sale) {
            this.item_sale = item_sale;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getSeller_nick() {
            return seller_nick;
        }

        public void setSeller_nick(String seller_nick) {
            this.seller_nick = seller_nick;
        }

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public String getSmall_images() {
            return small_images;
        }

        public void setSmall_images(String small_images) {
            this.small_images = small_images;
        }
    }

}
