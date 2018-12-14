package me.jessyan.peach.shop.entity.user;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/1/10 16:23
 * E-Mail: 15260828327@163.com
 * description:
 */

public class IntegralProblemBean {

    private List<DataModel> data;

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    public static class DataModel{
        private String title;
        private String url;

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
