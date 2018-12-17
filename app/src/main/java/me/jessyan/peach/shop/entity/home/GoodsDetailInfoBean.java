package me.jessyan.peach.shop.entity.home;

import com.google.gson.annotations.SerializedName;

import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Create by HuiRan on 2018/12/17 下午3:05
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailInfoBean {

    @SerializedName("couponendtime")
    private long couponEndTime;
    @SerializedName("couponmoney")
    private String couponMoney;
    @SerializedName("couponstarttime")
    private long couponStartTime;
    @SerializedName("itemendprice")
    private String discountPrice;
    @SerializedName("itemprice")
    private String originalPrice;
    @SerializedName("itemsale")
    private String soldCount;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String shopType;
    @SerializedName("tk_money")
    private String tkMoney;
    @SerializedName("guide_article")
    private String guideArticle;
    @SerializedName("id")
    private String itemId;
    //商品是否收藏
    private boolean isCollection;

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public long getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(long couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public long getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(long couponStartTime) {
        this.couponStartTime = couponStartTime;
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

    public String getSoldCount() {
        return StringUtils.getUnitString(soldCount);
    }

    public void setSoldCount(String soldCount) {
        this.soldCount = soldCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getTkMoney() {
        return tkMoney;
    }

    public void setTkMoney(String tkMoney) {
        this.tkMoney = tkMoney;
    }

    public String getGuideArticle() {
        return guideArticle;
    }

    public void setGuideArticle(String guideArticle) {
        this.guideArticle = guideArticle;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
