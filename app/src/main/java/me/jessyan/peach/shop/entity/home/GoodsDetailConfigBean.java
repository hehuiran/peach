package me.jessyan.peach.shop.entity.home;

import android.os.Parcel;
import android.os.Parcelable;

import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Created by HuiRan on 2018/5/23 16:41
 * E-Mail: 15260828327@163.com
 * description:
 */

public class GoodsDetailConfigBean implements Parcelable {

    private String itemId;
    private String imageUrl;
    private String couponMoney;
    private String title;
    private boolean isRequestDetailsApi;
    private long couponStartTime;
    private long couponEndTime;
    private int source;
    private String tkMoney;
    private String discountPrice;
    private String originalPrice;
    private String sellCount;


    private GoodsDetailConfigBean(Builder builder) {
        this.itemId = builder.itemId;
        this.imageUrl = builder.imageUrl;
        this.isRequestDetailsApi = builder.isRequestDetailsApi;
        this.couponStartTime = builder.couponStartTime;
        this.couponEndTime = builder.couponEndTime;
        this.couponMoney = builder.couponMoney;
        this.title = builder.title;
        this.source = builder.source;
        this.tkMoney = builder.tkMoney;
        this.discountPrice = builder.discountPrice;
        this.originalPrice = builder.originalPrice;
        this.sellCount = builder.sellCount;
    }


    protected GoodsDetailConfigBean(Parcel in) {
        itemId = in.readString();
        imageUrl = in.readString();
        couponMoney = in.readString();
        title = in.readString();
        isRequestDetailsApi = in.readByte() != 0;
        couponStartTime = in.readLong();
        couponEndTime = in.readLong();
        source = in.readInt();
        tkMoney = in.readString();
        discountPrice = in.readString();
        originalPrice = in.readString();
        sellCount = in.readString();
    }

    public static final Creator<GoodsDetailConfigBean> CREATOR = new Creator<GoodsDetailConfigBean>() {
        @Override
        public GoodsDetailConfigBean createFromParcel(Parcel in) {
            return new GoodsDetailConfigBean(in);
        }

        @Override
        public GoodsDetailConfigBean[] newArray(int size) {
            return new GoodsDetailConfigBean[size];
        }
    };

    public String getDiscountPrice() {
        return discountPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getSellCount() {
        return sellCount;
    }

    public String getTkMoney() {
        return tkMoney;
    }

    public int getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public String getItemId() {
        return itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isRequestDetailsApi() {
        return isRequestDetailsApi;
    }

    public long getCouponStartTime() {
        return couponStartTime;
    }

    public long getCouponEndTime() {
        return couponEndTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemId);
        dest.writeString(imageUrl);
        dest.writeString(couponMoney);
        dest.writeString(title);
        dest.writeByte((byte) (isRequestDetailsApi ? 1 : 0));
        dest.writeLong(couponStartTime);
        dest.writeLong(couponEndTime);
        dest.writeInt(source);
        dest.writeString(tkMoney);
        dest.writeString(discountPrice);
        dest.writeString(originalPrice);
        dest.writeString(sellCount);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String itemId;
        private String imageUrl;
        private String couponMoney;
        private String title;
        private boolean isRequestDetailsApi;
        private long couponStartTime;
        private long couponEndTime;
        private int source;
        private String tkMoney;

        private String discountPrice;
        private String originalPrice;
        private String sellCount;


        public Builder setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
            return this;
        }

        public Builder setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
            return this;
        }

        public Builder setSellCount(String sellCount) {
            this.sellCount = sellCount;
            return this;
        }

        public Builder setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setRequestDetailsApi(boolean requestDetailsApi) {
            isRequestDetailsApi = requestDetailsApi;
            return this;
        }

        public Builder setCouponStartTime(long couponStartTime) {
            this.couponStartTime = couponStartTime;
            return this;
        }

        public Builder setCouponEndTime(long couponEndTime) {
            this.couponEndTime = couponEndTime;
            return this;
        }

        public Builder setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSource(int source) {
            this.source = source;
            return this;
        }

        public Builder setTkMoney(String tkMoney) {
            this.tkMoney = tkMoney;
            return this;
        }

        public GoodsDetailConfigBean build() {
            return new GoodsDetailConfigBean(this);
        }
    }

    public static GoodsDetailConfigBean generateConfigBean(GoodsBean bean) {
        return generateConfigBean(bean, true);
    }

    public static GoodsDetailConfigBean generateConfigBean(GoodsBean bean, boolean isRequestDetailsApi) {
        return builder()
                .setDiscountPrice(bean.getDiscountPrice())
                .setOriginalPrice(bean.getOriginalPrice())
                .setSellCount(bean.getSoldCount())
                .setItemId(bean.getItemId())
                .setImageUrl(bean.getImg())
                .setRequestDetailsApi(isRequestDetailsApi)
                .setCouponStartTime(StringUtils.parseLong(bean.getCouponStartTime()))
                .setCouponEndTime(StringUtils.parseLong(bean.getCouponEndTime()))
                .setCouponMoney(bean.getCouponmoney())
                .setTitle(bean.getTitle())
                .setSource(bean.getSource())
                .setTkMoney(bean.getTkMoney())
                .build();
    }
}
