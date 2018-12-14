package me.jessyan.peach.shop.netconfig.transformer;

import android.support.annotation.NonNull;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Create by HuiRan on 2018/12/12 下午10:45
 * email: 15260828327@163.com
 * description:
 */
public class CommonTransformer<T> implements ObservableTransformer<T, T> {

    private BasePresenter mPresenter;

    public CommonTransformer(@NonNull BasePresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mPresenter.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mPresenter.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mPresenter.getRootView()));
    }

    /*public void onDestroy() {
        mPresenter = null;
        mView = null;
    }*/
}
