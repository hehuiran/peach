package me.jessyan.peach.shop.entity.search;

import java.util.List;

/**
 * author: Create by HuiRan on 2018/12/22 下午1:20
 * email: 15260828327@163.com
 * description:
 */
public class SearchOptionalBean {
    private List<SearchHotValueBean> hotList;
    private List<SearchRecordBean> recordList;

    public List<SearchRecordBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<SearchRecordBean> recordList) {
        this.recordList = recordList;
    }

    public List<SearchHotValueBean> getHotList() {
        return hotList;
    }

    public void setHotList(List<SearchHotValueBean> hotList) {
        this.hotList = hotList;
    }
}
