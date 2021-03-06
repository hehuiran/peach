package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.MyFansSubBean;
import me.jessyan.peach.shop.mine.mvp.contract.MyFansSubContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 19:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MyFansSubPresenter extends BasePresenter<MyFansSubContract.Model, MyFansSubContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public MyFansSubPresenter(MyFansSubContract.Model model, MyFansSubContract.View rootView) {
        super(model, rootView);
    }

    public void getMyFansList(String type, boolean pullToRefresh, boolean showLoading) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getMyFansList(type, page)
                .compose(new CommonTransformer<>(this, showLoading))
                .subscribe(new ErrorHandleSubscriber<Optional<MyFansSubBean>>(mErrorHandler) {
                    @Override
                    public void onNext(Optional<MyFansSubBean> myFansSubBeanOptional) {
                        mRootView.onGetMyFansListSuccess(myFansSubBeanOptional.isEmpty() ? null : myFansSubBeanOptional.get().getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetMyFansListFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
