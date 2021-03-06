package me.jessyan.peach.shop.widget.single;

import java.util.Comparator;

/**
 * author: Created by HuiRan on 2017/12/20 16:12
 * E-Mail: 15260828327@163.com
 * description:
 */

public class SingleTimeMillisComparator implements Comparator<SingleUnit> {
    @Override
    public int compare(SingleUnit o1, SingleUnit o2) {
        if (o1.getTimeMillis() == o2.getTimeMillis()) {
            return 0;
        } else if (o1.getTimeMillis() > o2.getTimeMillis()) {
            return 1;
        } else {
            return -1;
        }
    }
}
