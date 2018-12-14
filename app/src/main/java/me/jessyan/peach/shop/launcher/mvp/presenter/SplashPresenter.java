package me.jessyan.peach.shop.launcher.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.launcher.mvp.contract.SplashContract;
import me.jessyan.peach.shop.netconfig.RxSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/08/2018 21:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private Disposable mDisposable;

    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView) {
        super(model, rootView);
    }

    public void getAdvertising() {
        mModel.getAdvertising(String.valueOf(21))
                .compose(RxSchedulers.compose())
                .doOnSubscribe(disposable -> mDisposable = disposable)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CouponsBannerBean>(mErrorHandler) {
                    @Override
                    public void onNext(CouponsBannerBean couponsBannerBean) {
                        List<CouponsBannerBean.BannerBean> bannerList = couponsBannerBean.getBannerList();
                        if (!bannerList.isEmpty()) {
                            mRootView.onGetAdvertisingSuccess(bannerList.get(0));
                        } else {
                            mRootView.onGetAdvertisingFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetAdvertisingFailed();
                    }
                });

    }

    public void cancelTask() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        cancelTask();
    }
}
