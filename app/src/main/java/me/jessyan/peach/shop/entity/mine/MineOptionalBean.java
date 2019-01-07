package me.jessyan.peach.shop.entity.mine;

import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.user.UserAccountBean;

/**
 * author: Create by HuiRan on 2018/12/27 下午11:40
 * email: 15260828327@163.com
 * description:
 */
public class MineOptionalBean {

    private CouponsBannerBean.BannerBean mBannerBean;
    private UserAccountBean mAccountBean;

    public CouponsBannerBean.BannerBean getBannerBean() {
        return mBannerBean;
    }

    public void setBannerBean(CouponsBannerBean.BannerBean bannerBean) {
        mBannerBean = bannerBean;
    }

    public UserAccountBean getAccountBean() {
        return mAccountBean;
    }

    public void setAccountBean(UserAccountBean accountBean) {
        mAccountBean = accountBean;
    }

}
