package me.jessyan.peach.shop.entity.home;

import java.util.List;

/**
 * author: Create by HuiRan on 2018/12/17 下午3:13
 * email: 15260828327@163.com
 * description:
 */
public class GoodsDetailSellerBean {

    /**
     * userId : 2783478757
     * shopId : 148538347
     * shopName : 奥义健与美专卖店
     * shopUrl : tmall://page.tm/shop?item_id=527092570204&shopId=148538347
     * taoShopUrl : //shop.m.taobao.com/shop/shop_index.htm?user_id=2783478757&item_id=527092570204
     * shopIcon : //img.alicdn.com/imgextra//44/1e/TB13uOgRVXXXXcXXXXXwu0bFXXX.png
     * fans : 3.2万
     * allItemCount : 56
     * showShopLinkIcon : false
     * shopCard : 本店共56件宝贝在热卖
     * sellerType : B
     * shopType : B
     * evaluates : [{"title":"宝贝描述","score":"4.8 ","type":"desc","level":"1","levelText":"高","levelTextColor":"#FF5000","levelBackgroundColor":"#FFF1EB"},{"title":"卖家服务","score":"4.8 ","type":"serv","level":"1","levelText":"高","levelTextColor":"#FF5000","levelBackgroundColor":"#FFF1EB"},{"title":"物流服务","score":"4.8 ","type":"post","level":"1","levelText":"高","levelTextColor":"#FF5000","levelBackgroundColor":"#FFF1EB"}]
     * sellerNick : 奥义健与美专卖店
     * creditLevel : 16
     * creditLevelIcon : //img.alicdn.com/tfs/TB1u5sBnlfH8KJjy1XbXXbLdXXa-198-36.png?getAvatar=avatar
     * starts : 2016-02-16 15:31:23
     * goodRatePercentage : 100.00%
     */

    private String userId;
    private String shopId;
    private String shopName;
    private String shopUrl;
    private String taoShopUrl;
    private String shopIcon;
    private String fans;
    private String allItemCount;
    private boolean showShopLinkIcon;
    private String shopCard;
    private String sellerType;
    private String shopType;
    private String sellerNick;
    private String creditLevel;
    private String creditLevelIcon;
    private String starts;
    private String goodRatePercentage;
    private List<EvaluatesBean> evaluates;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getTaoShopUrl() {
        return taoShopUrl;
    }

    public void setTaoShopUrl(String taoShopUrl) {
        this.taoShopUrl = taoShopUrl;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getAllItemCount() {
        return allItemCount;
    }

    public void setAllItemCount(String allItemCount) {
        this.allItemCount = allItemCount;
    }

    public boolean isShowShopLinkIcon() {
        return showShopLinkIcon;
    }

    public void setShowShopLinkIcon(boolean showShopLinkIcon) {
        this.showShopLinkIcon = showShopLinkIcon;
    }

    public String getShopCard() {
        return shopCard;
    }

    public void setShopCard(String shopCard) {
        this.shopCard = shopCard;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getCreditLevelIcon() {
        return creditLevelIcon;
    }

    public void setCreditLevelIcon(String creditLevelIcon) {
        this.creditLevelIcon = creditLevelIcon;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String getGoodRatePercentage() {
        return goodRatePercentage;
    }

    public void setGoodRatePercentage(String goodRatePercentage) {
        this.goodRatePercentage = goodRatePercentage;
    }

    public List<EvaluatesBean> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<EvaluatesBean> evaluates) {
        this.evaluates = evaluates;
    }

    public static class EvaluatesBean {
        /**
         * title : 宝贝描述
         * score : 4.8
         * type : desc
         * level : 1
         * levelText : 高
         * levelTextColor : #FF5000
         * levelBackgroundColor : #FFF1EB
         */

        private String title;
        private String score;
        private String type;
        private String level;
        private String levelText;
        private String levelTextColor;
        private String levelBackgroundColor;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevelText() {
            return levelText;
        }

        public void setLevelText(String levelText) {
            this.levelText = levelText;
        }

        public String getLevelTextColor() {
            return levelTextColor;
        }

        public void setLevelTextColor(String levelTextColor) {
            this.levelTextColor = levelTextColor;
        }

        public String getLevelBackgroundColor() {
            return levelBackgroundColor;
        }

        public void setLevelBackgroundColor(String levelBackgroundColor) {
            this.levelBackgroundColor = levelBackgroundColor;
        }
    }
}
