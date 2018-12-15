package me.jessyan.peach.shop.home.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.goods.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.home.HomeMainOptionalBean;
import me.jessyan.peach.shop.home.mvp.contract.HomeMainContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeMainPresenter extends BasePresenter<HomeMainContract.Model, HomeMainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public HomeMainPresenter(HomeMainContract.Model model, HomeMainContract.View rootView) {
        super(model, rootView);
    }

    public void getHomeMainData() {
        page = CommonConstant.PAGE_INITIAL;
        mModel.getHomeMainData()
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<HomeMainOptionalBean>(mErrorHandler) {
                    @Override
                    public void onNext(HomeMainOptionalBean homeMainOptionalBean) {
                        page++;
                        mRootView.onGetHomeMainDataSuccess(homeMainOptionalBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetHomeMainDataFailed();
                    }
                });
    }

    public void loadMoreGoods() {
        mModel.loadMoreGoods(page)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<CouponsCommodityBean>(mErrorHandler) {
                    @Override
                    public void onNext(CouponsCommodityBean couponsCommodityBean) {
                        page++;
                        mRootView.onLoadMoreGoodsSuccess(couponsCommodityBean.getList());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onLoadMoreGoodsFailed();
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
