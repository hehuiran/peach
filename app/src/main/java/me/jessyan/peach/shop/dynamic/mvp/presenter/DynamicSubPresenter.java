package me.jessyan.peach.shop.dynamic.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.dynamic.mvp.contract.DynamicSubContract;
import me.jessyan.peach.shop.entity.DynamicBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2018 23:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class DynamicSubPresenter extends BasePresenter<DynamicSubContract.Model, DynamicSubContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public DynamicSubPresenter(DynamicSubContract.Model model, DynamicSubContract.View rootView) {
        super(model, rootView);
    }

    public void getDynamicData(int type, boolean pullToRefresh,boolean showLoading) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getDynamicData(type, page)
                .compose(new CommonTransformer<>(this,showLoading))
                .subscribe(new ErrorHandleSubscriber<DynamicBean>(mErrorHandler) {
                    @Override
                    public void onNext(DynamicBean dynamicBean) {
                        mRootView.onGetDynamicSuccess(dynamicBean.getList());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetDynamicFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
