package me.jessyan.peach.shop.entity.home;

import com.google.gson.annotations.SerializedName;

import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.MultiItemBean;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Create by HuiRan on 2018/12/16 下午11:51
 * email: 15260828327@163.com
 * description:
 */
public class GoodsBean extends MultiItemBean {

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
    @SerializedName("guide_article")
    private String guideArticle;



    @Override
    public GoodsBean clone() {
        try {
            return (GoodsBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGuideArticle() {
        return guideArticle;
    }

    public void setGuideArticle(String guideArticle) {
        this.guideArticle = guideArticle;
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
        return img;
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

    @Override
    public int getItemType() {
        int itemType = super.getItemType();
        return itemType == 0 ? RecyclerViewType.HOME_GOODS_TYPE : itemType;
    }
}
