package me.jessyan.peach.shop.entity.user;

import com.google.gson.annotations.SerializedName;

/**
 * author: Created by HuiRan on 2017/12/28 10:31
 * E-Mail: 15260828327@163.com
 * description:
 */

public class LoginBean {

    private String salt;
    private String token;
    private String alipayAccount;
    private String realName;
    @SerializedName("inviter")
    private String subordinate;
    @SerializedName("user")
    private UserBean user;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "salt='" + salt + '\'' +
                ", token='" + token + '\'' +
                ", alipayAccount='" + alipayAccount + '\'' +
                ", realName='" + realName + '\'' +
                ", subordinate='" + subordinate + '\'' +
                ", user=" + user.toString() +
                '}';
    }

    public String getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(String subordinate) {
        this.subordinate = subordinate;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
