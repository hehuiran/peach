package me.jessyan.peach.shop.entity.home;

import java.util.List;

import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.CouponsChannelBean;
import me.jessyan.peach.shop.entity.goods.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.goods.OrientationGoodsBean;

/**
 * author: Create by HuiRan on 2018/12/14 下午10:33
 * email: 15260828327@163.com
 * description:
 */
public class HomeMainOptionalBean {

    //banner
    private CouponsBannerBean bannerData;

    //子分类 ： 10大金刚
    private CouponsChannelBean.ChannelModel channelData;

    //四大板块
    private List<HomeSectionBean> homeSectionList;

    //每日推荐
    private OrientationGoodsBean orientationGoodsBean;

    //商品
    private CouponsCommodityBean couponsCommodityBean;

    public CouponsBannerBean getBannerData() {
        return bannerData;
    }

    public void setBannerData(CouponsBannerBean bannerData) {
        this.bannerData = bannerData;
    }

    public CouponsChannelBean.ChannelModel getChannelData() {
        return channelData;
    }

    public void setChannelData(CouponsChannelBean.ChannelModel channelData) {
        this.channelData = channelData;
    }

    public OrientationGoodsBean getOrientationGoodsBean() {
        return orientationGoodsBean;
    }

    public void setOrientationGoodsBean(OrientationGoodsBean orientationGoodsBean) {
        this.orientationGoodsBean = orientationGoodsBean;
    }

    public CouponsCommodityBean getCouponsCommodityBean() {
        return couponsCommodityBean;
    }

    public void setCouponsCommodityBean(CouponsCommodityBean couponsCommodityBean) {
        this.couponsCommodityBean = couponsCommodityBean;
    }

    public List<HomeSectionBean> getHomeSectionList() {
        return homeSectionList;
    }

    public void setHomeSectionList(List<HomeSectionBean> homeSectionList) {
        this.homeSectionList = homeSectionList;
    }
}
