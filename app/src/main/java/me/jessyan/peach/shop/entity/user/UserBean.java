package me.jessyan.peach.shop.entity.user;

/**
 * author: Created by HuiRan on 2017/12/28 10:33
 * E-Mail: 15260828327@163.com
 * description:
 */

public class UserBean {

    //{"usergrade":"","useridentity":"","wx_number":"","addTime":"","gender":0,"signStatus":0,"qqOpenid":"","pid":"","type":0,"cardNo":"","password":"","isDeleted":0,"headimgurl":"","is_visibility":0,"nickname":"","deviceSerialId":"","id":0,"shopping":0,"deviceType":0,"inviterMobile":"","wxOpenid":"","inviter_time":"","cardType":0,"mobile":"","usergradetime":"","alipayAccount":"","version":"","realName":"","inviter_code":"","username":""}}
    /**
     * usergrade :
     * useridentity :
     * wx_number :
     * addTime :
     * gender : 0
     * signStatus : 0
     * qqOpenid :
     * pid :
     * type : 0
     * cardNo :
     * password :
     * isDeleted : 0
     * headimgurl :
     * is_visibility : 0
     * nickname :
     * deviceSerialId :
     * id : 0
     * shopping : 0
     * deviceType : 0
     * inviterMobile :
     * wxOpenid :
     * inviter_time :
     * cardType : 0
     * mobile :
     * usergradetime :
     * alipayAccount :
     * version :
     * realName :
     * inviter_code :
     * username :
     */



    /**
     * id : null
     * pid : mm_52605298_40122705_180776948
     * medisId : 180776948
     * advId : null
     * username : null
     * password : null
     * mobile : 15260828327
     * nickname : null
     * deviceType : null
     * wxOpenid : null
     * qqOpenid : 4F60BA0669397B3A82F90ADB741E27E5
     * version : null
     * headimgurl : null
     * type : null
     * gender : null
     * deviceSerialId : null
     * realName : null
     * cardType : null
     * cardNo : null
     * alipayAccount : null
     * shopping : null
     * addTime : null
     * isDeleted : null
     */

    private String id;
    private String pid;
    private String password;
    private String mobile;
    private String nickname;
    private String deviceType;
    private String wxOpenid;
    private String qqOpenid;
    private String version;
    private String headimgurl;
    private String deviceSerialId;
    private String inviterMobile;
    private String usergrade;
    private String useridentity;
    private String inviter_code;
    private int is_visibility;      //是否将微信显示给下级
    private String wx_number;       //用户微信
    private int isDeleted;          //账户状态  0：正常；1：删除； 2： 冻结

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", wxOpenid='" + wxOpenid + '\'' +
                ", qqOpenid='" + qqOpenid + '\'' +
                ", version='" + version + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", deviceSerialId='" + deviceSerialId + '\'' +
                ", inviterMobile='" + inviterMobile + '\'' +
                ", usergrade='" + usergrade + '\'' +
                ", useridentity='" + useridentity + '\'' +
                ", inviter_code='" + inviter_code + '\'' +
                ", wx_number='" + wx_number + '\'' +
                ", is_visibility='" + is_visibility + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                '}';
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getIs_visibility() {
        return is_visibility;
    }

    public void setIs_visibility(int is_visibility) {
        this.is_visibility = is_visibility;
    }

    public String getWx_number() {
        return wx_number;
    }

    public void setWx_number(String wx_number) {
        this.wx_number = wx_number;
    }

    public String getUsergrade() {
        return usergrade;
    }

    public void setUsergrade(String usergrade) {
        this.usergrade = usergrade;
    }

    public String getUseridentity() {
        return useridentity;
    }

    public void setUseridentity(String useridentity) {
        this.useridentity = useridentity;
    }

    public String getInviter_code() {
        return inviter_code;
    }

    public void setInviter_code(String inviter_code) {
        this.inviter_code = inviter_code;
    }

    public String getInviterMobile() {
        return inviterMobile;
    }

    public void setInviterMobile(String inviterMobile) {
        this.inviterMobile = inviterMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getDeviceSerialId() {
        return deviceSerialId;
    }

    public void setDeviceSerialId(String deviceSerialId) {
        this.deviceSerialId = deviceSerialId;
    }
}
