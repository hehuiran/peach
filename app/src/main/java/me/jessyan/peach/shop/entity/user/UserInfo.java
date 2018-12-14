package me.jessyan.peach.shop.entity.user;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.SPKey;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * Created by Administrator on 2017/11/17.
 *
 */

public class UserInfo {

    private static volatile UserInfo instance;
    private static final String TAG = "UserInfo";

    private String nickname;
    private String id;
    private String headImgUrl;
    private String mobile;
    private String password;
    private String wxOpenid;
    private String qqOpenid;

    private String alipay;
    private String realName;
    private String pid;
    private String adZoneId;

    private String searchUrl;
    private String inviteMobile;
    private String token;
    private String salt;
    private String identity;
    private int identityStatus;
    private String inviteCode;
    private int subordinate;


    public String getAdZoneId() {
        return adZoneId;
    }

    public void setAdZoneId(String adZoneId) {
        this.adZoneId = adZoneId;
    }

    public int getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(int subordinate) {
        this.subordinate = subordinate;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(int identityStatus) {
        this.identityStatus = identityStatus;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getToken() {
        return TextUtils.isEmpty(token) ?
                SPUtils.getInstance().getString(SPKey.TOKEN) : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInviteMobile() {
        return inviteMobile;
    }

    public void setInviteMobile(String inviteMobile) {
        this.inviteMobile = inviteMobile;
    }

    @Deprecated
    public String getSearchUrl() {
        return searchUrl;
    }

    @Deprecated
    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    private UserInfo() {

    }

    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    public void setData(LoginBean loginBean) {
        /*boolean isAliasState = SPUtils.getInstance().getBoolean(CommonConstant.JPUSH_ALIAS_STATE, false);
        if (!isAliasState) {
            //设置alias
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
            tagAliasBean.isAliasAction = true;
            tagAliasBean.alias = loginBean.getUser().getMobile();

            TagAliasOperatorHelper.getInstance().handleAction(1, tagAliasBean);

            //设置tag
            TagAliasOperatorHelper.TagAliasBean tagBean = new TagAliasOperatorHelper.TagAliasBean();
            tagBean.action = TagAliasOperatorHelper.ACTION_SET;
            tagBean.isAliasAction = false;
            Set<String> tags = new HashSet<>();
            tags.add(MyTools.getInstance().getDeviceSingleTag());
            tagBean.tags = tags;
            TagAliasOperatorHelper.getInstance().handleAction(2, tagBean);
        }*/

        UserBean user = loginBean.getUser();

        setSalt(loginBean.getSalt());
        setToken(loginBean.getToken());
        setAlipay(loginBean.getAlipayAccount());
        setRealName(loginBean.getRealName());
        setSubordinate(StringUtils.NumberFormat(loginBean.getSubordinate()));

        setHeadImgUrl(user.getHeadimgurl());
        setId(user.getId());
        String nickname = user.getNickname();
        if (TextUtils.isEmpty(nickname)) {
            String phone = user.getMobile();
            String substring = phone.substring(3, 7);
            String mobile = phone.replaceAll(substring, "****");
            setNickname(mobile);
        } else {
            setNickname(nickname);
        }
        setPassword(user.getPassword());
        setMobile(user.getMobile());
        setQqOpenid(user.getQqOpenid());
        setWxOpenid(user.getWxOpenid());
        //自身微信
//        setWx_number(user.getWx_number());
//        setIs_visibility(user.getIs_visibility());

        String pid = user.getPid();
        setPid(TextUtils.isEmpty(pid) ? CommonConstant.PID : pid);
        String[] pids = getPid().split("_");
        setAdZoneId(pids[pids.length - 1]);

        setInviteMobile(user.getInviterMobile());
        setIdentity(user.getUsergrade());
        setIdentityStatus(StringUtils.NumberFormat(TextUtils.isEmpty(user.getUseridentity()) ? "0" : user.getUseridentity()));
        String inviteCode = user.getInviter_code();
        setInviteCode(TextUtils.isEmpty(inviteCode) ? String.valueOf(user.getId()) : inviteCode);

        SPUtils.getInstance().put(SPKey.TOKEN, loginBean.getToken());
        SPUtils.getInstance().put(SPKey.INVITE_CODE, loginBean.getToken());
        SPUtils.getInstance().put(SPKey.USER_GRADE, loginBean.getToken());
        SPUtils.getInstance().put(SPKey.ISDELETED, loginBean.getToken());

    }

    public void setNull() {
        setToken(null);
        //删除用户token
        SPUtils.getInstance().remove(SPKey.TOKEN);

        setSalt(null);

        setRealName(null);

        setAlipay(null);

        setHeadImgUrl(null);

        setId(null);

        setNickname(null);

        setPassword(null);

        setMobile(null);

        setQqOpenid(null);


        setWxOpenid(null);
        setPid(null);
        setAdZoneId(null);

        setInviteMobile(null);


        setIdentity(null);
        SPUtils.getInstance().remove(SPKey.IDENTITY_LEVEL);
        SPUtils.getInstance().remove(SPKey.USER_GRADE);

        setIdentityStatus(0);

        setInviteCode(null);
        SPUtils.getInstance().remove(SPKey.INVITE_CODE);

        setSubordinate(0);


        //删除极光别名
        /*TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(1, tagAliasBean);
        //删除极光tag
                                        *//*TagAliasOperatorHelper.TagAliasBean tagAliasBean2 = new TagAliasOperatorHelper.TagAliasBean();
                                        tagAliasBean2.action = TagAliasOperatorHelper.ACTION_DELETE;*//*
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(2, tagAliasBean);*/


    }

    /**
     * 检查用户是否登录
     */
    public boolean isLogin(){
        return !TextUtils.isEmpty(getToken());
    }
}
