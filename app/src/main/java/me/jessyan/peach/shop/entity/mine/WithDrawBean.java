package me.jessyan.peach.shop.entity.mine;

/**
 * author: Created by HuiRan on 2018/1/6 14:47
 * E-Mail: 15260828327@163.com
 * description:
 */

public class WithDrawBean {

    private String alipayAccount;
    private String withdrawMoney;
    private String totalAccount;

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(String totalAccount) {
        this.totalAccount = totalAccount;
    }
}
