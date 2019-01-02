package me.jessyan.peach.shop.entity.group;

import java.util.List;

import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;

/**
 * author: Create by HuiRan on 2019/1/1 下午1:51
 * email: 15260828327@163.com
 * description:
 */
public class GroupOptionalBean {

    private List<CouponsBannerBean.BannerBean> bannerList;
    private GroupBean mGroupBean;

    public List<CouponsBannerBean.BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<CouponsBannerBean.BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public GroupBean getGroupBean() {
        return mGroupBean;
    }

    public void setGroupBean(GroupBean groupBean) {
        mGroupBean = groupBean;
    }
}
