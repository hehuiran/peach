package me.jessyan.peach.shop.entity.mine;

import me.jessyan.peach.shop.entity.user.UserAccountBean;

/**
 * author: Create by HuiRan on 2018/12/27 下午11:40
 * email: 15260828327@163.com
 * description:
 */
public class MineOptionalBean {

    private String advertising;
    private UserAccountBean mAccountBean;

    public UserAccountBean getAccountBean() {
        return mAccountBean;
    }

    public void setAccountBean(UserAccountBean accountBean) {
        mAccountBean = accountBean;
    }

    public String getAdvertising() {
        return advertising;
    }

    public void setAdvertising(String advertising) {
        this.advertising = advertising;
    }
}
