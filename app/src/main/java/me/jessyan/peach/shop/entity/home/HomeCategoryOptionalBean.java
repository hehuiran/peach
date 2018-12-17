package me.jessyan.peach.shop.entity.home;

import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;

/**
 * author: Create by HuiRan on 2018/12/16 下午2:50
 * email: 15260828327@163.com
 * description:
 */
public class HomeCategoryOptionalBean {

    //banner
    private CouponsBannerBean bannerData;

    //子分类 ： 10大金刚
    private GoodsCategoryGridBean channelData;

    //商品
    private CouponsCommodityBean couponsCommodityBean;

    public CouponsBannerBean getBannerData() {
        return bannerData;
    }

    public void setBannerData(CouponsBannerBean bannerData) {
        this.bannerData = bannerData;
    }

    public GoodsCategoryGridBean getChannelData() {
        return channelData;
    }

    public void setChannelData(GoodsCategoryGridBean channelData) {
        this.channelData = channelData;
    }

    public CouponsCommodityBean getCouponsCommodityBean() {
        return couponsCommodityBean;
    }

    public void setCouponsCommodityBean(CouponsCommodityBean couponsCommodityBean) {
        this.couponsCommodityBean = couponsCommodityBean;
    }
}
