package me.jessyan.peach.shop.entity.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Create by HuiRan on 2018/12/17 下午3:05
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailInfoBean implements Parcelable {

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

    public GoodsDetailInfoBean() {

    }

    protected GoodsDetailInfoBean(Parcel in) {
        couponEndTime = in.readLong();
        couponMoney = in.readString();
        couponStartTime = in.readLong();
        discountPrice = in.readString();
        originalPrice = in.readString();
        soldCount = in.readString();
        title = in.readString();
        shopType = in.readString();
        tkMoney = in.readString();
        guideArticle = in.readString();
        itemId = in.readString();
        isCollection = in.readByte() != 0;
    }

    public static final Creator<GoodsDetailInfoBean> CREATOR = new Creator<GoodsDetailInfoBean>() {
        @Override
        public GoodsDetailInfoBean createFromParcel(Parcel in) {
            return new GoodsDetailInfoBean(in);
        }

        @Override
        public GoodsDetailInfoBean[] newArray(int size) {
            return new GoodsDetailInfoBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(couponEndTime);
        dest.writeString(couponMoney);
        dest.writeLong(couponStartTime);
        dest.writeString(discountPrice);
        dest.writeString(originalPrice);
        dest.writeString(soldCount);
        dest.writeString(title);
        dest.writeString(shopType);
        dest.writeString(tkMoney);
        dest.writeString(guideArticle);
        dest.writeString(itemId);
        dest.writeByte((byte) (isCollection ? 1 : 0));
    }
}
