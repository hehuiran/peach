package me.jessyan.peach.shop.entity.user;

import com.google.gson.annotations.SerializedName;

/**
 * author: Create by HuiRan on 2018/12/27 下午11:36
 * email: 15260828327@163.com
 * description:
 */
public class UserAccountBean {

    @SerializedName("data")
    private String saveMoney;
    private String income;
    @SerializedName("avaiAmount")
    private String balance;

    public String getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(String saveMoney) {
        this.saveMoney = saveMoney;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
