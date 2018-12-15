package me.jessyan.peach.shop.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Create by HuiRan on 2018/12/15 下午7:46
 * email: 15260828327@163.com
 * description:
 */
public class CouponsLayout extends LinearLayout {

    private TextView mTvMoney;

    public CouponsLayout(Context context) {
        super(context);
        initView();
    }

    public CouponsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CouponsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.coupons_layout, this, true);
        mTvMoney = inflate.findViewById(R.id.tv_money);
    }

    public void setMoney(int money) {
        setMoney(String.valueOf(money));
    }

    public void setMoney(String money) {
        String text = String.format(ResourceUtils.getResourceString(R.string.money), money);
        setText(text);
    }

    public void setText(CharSequence text) {
        if (mTvMoney != null) {
            mTvMoney.setText(text);
        }
    }
}
