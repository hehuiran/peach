package me.jessyan.peach.shop.entity.event;

/**
 * author: Create by HuiRan on 2019/1/7 下午3:09
 * email: 15260828327@163.com
 * description:
 */
public final class ChangeTabEvent {
    private int position;

    public ChangeTabEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
