package me.jessyan.peach.shop.entity.mine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/12 10:00
 * E-Mail: 15260828327@163.com
 * description:
 */

public class MineInfoAllianceBean extends MultiItemBean {

    @SerializedName("InvitationNumber")
    private String totalCount;
    @SerializedName("increaseNumber")
    private String addedCount;
    @SerializedName("data")
    private List<DataModel> iconList;

    public List<DataModel> getIconList() {
        return iconList;
    }

    public void setIconList(List<DataModel> iconList) {
        this.iconList = iconList;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getAddedCount() {
        return addedCount;
    }

    public void setAddedCount(String addedCount) {
        this.addedCount = addedCount;
    }

    public static class DataModel{

        /**
         * headimgurl : https://hzcangyu.com/152376287346820180415112751.jpg
         */

        private String headimgurl;

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }
    }
}
