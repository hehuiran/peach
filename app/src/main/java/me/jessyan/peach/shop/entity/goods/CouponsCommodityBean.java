package me.jessyan.peach.shop.entity.goods;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;
import me.jessyan.peach.shop.utils.GoodsUtils;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Created by HuiRan on 2017/12/21 19:07
 * E-Mail: 15260828327@163.com
 * description: 商品数据
 */

public class CouponsCommodityBean extends MultiItemBean {

    @SerializedName("data")
    private List<CommodityModel> list;

    private String data_timestamp;

    public String getDataTimeStamp() {
        return data_timestamp;
    }

    public void setDataTimeStamp(String data_timestamp) {
        this.data_timestamp = data_timestamp;
    }

    public List<CommodityModel> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<CommodityModel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CouponsCommodityBean{" +
                "list=" + list +
                '}';
    }

    @Override
    public CouponsCommodityBean clone() {
        try {
            CouponsCommodityBean resultsModel = (CouponsCommodityBean) super.clone();
            List<CommodityModel> list = new ArrayList<>();
            for (CommodityModel recommendModel : resultsModel.list) {
                list.add(recommendModel.clone());
            }
            resultsModel.setList(list);

            return resultsModel;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class CommodityModel extends MultiItemBean {

        @SerializedName("item_pic")
        private String img;
        @SerializedName("item_title")
        private String title;
        @SerializedName("item_endprice")
        private String discountPrice;
        @SerializedName("item_price")
        private String originalPrice;
        @SerializedName("item_sale")
        private String soldCount;
        @SerializedName("coupon_money")
        private String couponmoney;
        @SerializedName("item_id")
        private String itemId;
        @SerializedName("shop_type")
        private String shoptype;
        @SerializedName("seller_nick")
        private String sellernick;
        @SerializedName("seller_id")
        private String sellerId;
        @SerializedName("couponstart_time")
        private String couponStartTime;
        @SerializedName("couponend_time")
        private String couponEndTime;
        @SerializedName("tk_money")
        private String tkMoney;
        @SerializedName("source")
        private int source;             // 0 = 淘宝； 1 = 好单库； 2 = 大淘客 （正常显示）

        private String guide_article;

        public String getGuide_article() {
            return guide_article;
        }

        public void setGuide_article(String guide_article) {
            this.guide_article = guide_article;
        }

        public CommodityModel() {
        }

        public CommodityModel(String img, String title, String discountPrice, String originalPrice, String soldCount, String couponmoney, String itemId, String shoptype, String sellernick, String sellerId, String couponStartTime, String couponEndTime, String tkMoney, int source) {
            this.img = img;
            this.title = title;
            this.discountPrice = discountPrice;
            this.originalPrice = originalPrice;
            this.soldCount = soldCount;
            this.couponmoney = couponmoney;
            this.itemId = itemId;
            this.shoptype = shoptype;
            this.sellernick = sellernick;
            this.sellerId = sellerId;
            this.couponStartTime = couponStartTime;
            this.couponEndTime = couponEndTime;
            this.tkMoney = tkMoney;
            this.source = source;
        }

        @Override
        public CommodityModel clone() {
            try {
                return (CommodityModel) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getTkMoney() {
            return tkMoney;
        }

        public void setTkMoney(String tkMoney) {
            this.tkMoney = tkMoney;
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

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getCouponmoney() {
            return couponmoney;
        }

        public void setCouponmoney(String couponmoney) {
            this.couponmoney = couponmoney;
        }

        public String getSoldCount() {
            return StringUtils.getUnitString(soldCount);
        }

        public void setSoldCount(String soldCount) {
            this.soldCount = soldCount;
        }

        public String getImg() {
            return GoodsUtils.getSpecifiedSizeImageUrl(img, 400);
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }
    }
}
