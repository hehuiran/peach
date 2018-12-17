package me.jessyan.peach.shop.entity.home;

import java.util.List;

/**
 * author: Create by HuiRan on 2018/12/17 下午2:55
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailOptionalBean {

    private List<String> bannerList;
    private GoodsDetailInfoBean mGoodsDetailInfoBean;
    private GoodsDetailSellerBean mGoodsDetailSellerBean;
    private List<GoodsDetailImageBean> imageList;
    private List<GoodsBean> recommendList;

    public List<String> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<String> bannerList) {
        this.bannerList = bannerList;
    }

    public GoodsDetailInfoBean getGoodsDetailInfoBean() {
        return mGoodsDetailInfoBean;
    }

    public void setGoodsDetailInfoBean(GoodsDetailInfoBean goodsDetailInfoBean) {
        mGoodsDetailInfoBean = goodsDetailInfoBean;
    }

    public GoodsDetailSellerBean getGoodsDetailSellerBean() {
        return mGoodsDetailSellerBean;
    }

    public void setGoodsDetailSellerBean(GoodsDetailSellerBean goodsDetailSellerBean) {
        mGoodsDetailSellerBean = goodsDetailSellerBean;
    }

    public List<GoodsDetailImageBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<GoodsDetailImageBean> imageList) {
        this.imageList = imageList;
    }

    public List<GoodsBean> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<GoodsBean> recommendList) {
        this.recommendList = recommendList;
    }
}
