package me.jessyan.peach.shop.entity.mine;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.TimeUtils;

import me.jessyan.peach.shop.entity.MultiItemBean;

/**
 * author: Created by HuiRan on 2018/4/14 15:02
 * E-Mail: 15260828327@163.com
 * description:
 */

public class AllianceManagementListOrderBean extends MultiItemBean implements Comparable<AllianceManagementListOrderBean> {


    /**
     * commodity_name : 华为荣耀V10手机壳硅胶荣耀9男青春版手机套V9女款v9play全包防摔
     * purchase_time : 2018-03-30 21:17:15.0
     * commodity_price : 1.90
     * commission : 0
     * img : http://img.alicdn.com/bao/uploaded/i1/2129034002/TB2hVfFcN1YBuNjy1zcXXbNcXXa_!!2129034002.jpg_200x200.jpg
     * headimgurl : ["https://hzcangyu.com/152376287346820180415112751.jpg","成都"]
     * nickname : 成都
     * orderType : B
     * type : 1
     */

    private String commodity_name;
    private String purchase_time;
    private String commodity_price;
    private String commission;
    private String img;
    private String nickname;
    private String orderType;
    private String type;
    private String headimgurl;

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getPurchase_time() {
        return purchase_time;
    }

    public void setPurchase_time(String purchase_time) {
        this.purchase_time = purchase_time;
    }

    public String getCommodity_price() {
        return commodity_price;
    }

    public void setCommodity_price(String commodity_price) {
        this.commodity_price = commodity_price;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    @Override
    public int compareTo(@NonNull AllianceManagementListOrderBean o) {
        long l = TimeUtils.string2Millis(this.getPurchase_time());
        long ol = TimeUtils.string2Millis(o.getPurchase_time());
        if (l == ol) {
            return 0;
        } else if (l > ol) {
            return -1;
        } else {
            return 1;
        }
    }
}
