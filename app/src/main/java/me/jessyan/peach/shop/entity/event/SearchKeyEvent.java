package me.jessyan.peach.shop.entity.event;

import java.util.ArrayList;

/**
 * author: Create by HuiRan on 2018/12/12 下午8:44
 * email: 15260828327@163.com
 * description:
 */
public class SearchKeyEvent {
    private ArrayList<String> searchList;

    public SearchKeyEvent(ArrayList<String> searchList) {
        this.searchList = searchList;
    }

    public ArrayList<String> getSearchList() {
        return searchList;
    }
}
