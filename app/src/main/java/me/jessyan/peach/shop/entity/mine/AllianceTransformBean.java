package me.jessyan.peach.shop.entity.mine;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/13 19:41
 * E-Mail: 15260828327@163.com
 * description:
 */

public class AllianceTransformBean {

    private MultiItemBean teamModel;
    private AllianceManagementIdentityBean identityModel;
    private AllianceManagementStickyBean stickyModel;
    private List<MultiItemBean> list;
    private List<AllianceManagementImgBean> imgEmptyList;

    public List<AllianceManagementImgBean> getImgEmptyList() {
        return imgEmptyList;
    }

    public void setImgEmptyList(List<AllianceManagementImgBean> imgEmptyList) {
        this.imgEmptyList = imgEmptyList;
    }

    public AllianceManagementIdentityBean getIdentityModel() {
        return identityModel;
    }

    public void setIdentityModel(AllianceManagementIdentityBean identityModel) {
        this.identityModel = identityModel;
    }

    public List<MultiItemBean> getList() {
        return list;
    }

    public void setList(List<MultiItemBean> list) {
        this.list = list;
    }

    public AllianceManagementStickyBean getStickyModel() {
        return stickyModel;
    }

    public void setStickyModel(AllianceManagementStickyBean stickyModel) {
        this.stickyModel = stickyModel;
    }

    public void setTeamModel(MultiItemBean teamModel) {
        this.teamModel = teamModel;
    }

    public MultiItemBean getTeamModel() {
        return teamModel;
    }
}
