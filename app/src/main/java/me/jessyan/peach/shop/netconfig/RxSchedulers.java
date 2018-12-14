package me.jessyan.peach.shop.netconfig;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author Created by He on 2017/8/24.
 * description
 */

public final class RxSchedulers {

    private RxSchedulers() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 线程调度
     */
    public static <T> ObservableTransformer<T, T> compose() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
