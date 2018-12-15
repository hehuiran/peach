package me.jessyan.peach.shop.netconfig.transformer;

import android.support.annotation.NonNull;

import com.jess.arms.mvp.IPresenter;
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

    private IPresenter mPresenter;
    private boolean showLoading;

    public CommonTransformer(@NonNull IPresenter presenter) {
        this(presenter, true);
    }

    public CommonTransformer(@NonNull IPresenter presenter, boolean showLoading) {
        this.mPresenter = presenter;
        this.showLoading = showLoading;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        ObservableSource<T> source;
        if (showLoading) {
            source = upstream
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> mPresenter.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mPresenter.hideLoading())
                    .compose(RxLifecycleUtils.bindToLifecycle(mPresenter.getView()));
        } else {
            source = upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(mPresenter.getView()));
        }
        return source;
    }

    /*public void onDestroy() {
        mPresenter = null;
        mView = null;
    }*/
}
