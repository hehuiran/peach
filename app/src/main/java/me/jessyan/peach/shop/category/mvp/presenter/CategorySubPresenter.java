package me.jessyan.peach.shop.category.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.category.mvp.contract.CategorySubContract;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 21:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CategorySubPresenter extends BasePresenter<CategorySubContract.Model, CategorySubContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;
    private String dataTimeStamp = CommonConstant.EMPTY_STRING;

    @Inject
    public CategorySubPresenter(CategorySubContract.Model model, CategorySubContract.View rootView) {
        super(model, rootView);
    }

    public void fetchCategorySubData(boolean pullToRefresh, boolean showLoading,
                                     String oneType, String twoType, String sort) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.fetchCategorySubData(page, oneType, twoType, sort, dataTimeStamp)
                .compose(new CommonTransformer<>(this, showLoading))
                .subscribe(new ErrorHandleSubscriber<CouponsCommodityBean>(mErrorHandler) {
                    @Override
                    public void onNext(CouponsCommodityBean couponsCommodityBean) {
                        if (pullToRefresh) {
                            dataTimeStamp = couponsCommodityBean.getDataTimestamp();
                        }
                        page++;
                        mRootView.onFetchCategorySubDataSuccess(couponsCommodityBean.getList());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFetchCategorySubDataFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
