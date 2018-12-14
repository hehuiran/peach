package me.jessyan.peach.shop.callback;

import android.view.View;

import me.jessyan.peach.shop.utils.ClickUtils;


/**
 * author: Created by HuiRan on 2018/7/10 11:58
 * E-Mail: 15260828327@163.com
 * description: 防止多次点击的回调接口
 */
public abstract class OnSingleClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (ClickUtils.isNormalClick(v)) {
            onClicked(v);
        }
    }

    public abstract void onClicked(View view);
}
