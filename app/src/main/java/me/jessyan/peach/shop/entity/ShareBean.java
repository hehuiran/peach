package me.jessyan.peach.shop.entity;

/**
 * author: Created by HuiRan on 2018/1/16 22:00
 * E-Mail: 15260828327@163.com
 * description:
 */

public class ShareBean {

    /**
     * wxShareModel : {"description":"描述","icon":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1516109985&di=8ba926b348022e1c06662c6573aca330&src=http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1205/05/c9/11500358_11500358_1336221233152_mthumb.jpg","title":"测试","url":"http://o196r70901.iok.la/tz/page1.html?token="}
     */

    private WxShareModel wxShareModel;

    public WxShareModel getWxShareModel() {
        return wxShareModel;
    }

    public void setWxShareModel(WxShareModel wxShareModel) {
        this.wxShareModel = wxShareModel;
    }

    public static class WxShareModel {
        /**
         * description : 描述
         * icon : https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1516109985&di=8ba926b348022e1c06662c6573aca330&src=http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1205/05/c9/11500358_11500358_1336221233152_mthumb.jpg
         * title : 测试
         * url : http://o196r70901.iok.la/tz/page1.html?token=
         */

        private String description;
        private String icon;
        private String title;
        private String url;
        private String inviter_code;

        @Override
        public String toString() {
            return "WxShareModel{" +
                    "description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
