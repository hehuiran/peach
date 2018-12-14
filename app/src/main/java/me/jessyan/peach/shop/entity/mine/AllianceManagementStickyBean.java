package me.jessyan.peach.shop.entity.mine;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/14 11:34
 * E-Mail: 15260828327@163.com
 * description:
 */

public class AllianceManagementStickyBean extends MultiItemBean {
    private List<TabModel> tabList;

    public List<TabModel> getTabList() {
        return tabList;
    }

    public void setTabList(List<TabModel> tabList) {
        this.tabList = tabList;
    }

    public static class TabModel {
        private String title;
        private boolean checked;
        private int orderType;

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
