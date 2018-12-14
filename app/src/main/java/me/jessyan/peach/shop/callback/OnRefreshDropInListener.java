package me.jessyan.peach.shop.callback;

/**
 * author: Created by HuiRan on 2017/12/19 11:37
 * E-Mail: 15260828327@163.com
 * description: 下拉刷新下拉Y轴偏移监听
 */

public interface OnRefreshDropInListener {
    void dropIn(float currentPercent, int currentPosY);
}
