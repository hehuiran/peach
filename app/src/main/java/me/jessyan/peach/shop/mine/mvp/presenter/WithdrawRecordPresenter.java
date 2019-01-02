package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.WithdrawRecordBean;
import me.jessyan.peach.shop.mine.mvp.contract.WithdrawRecordContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 12:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WithdrawRecordPresenter extends BasePresenter<WithdrawRecordContract.Model, WithdrawRecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public WithdrawRecordPresenter(WithdrawRecordContract.Model model, WithdrawRecordContract.View rootView) {
        super(model, rootView);
    }

    public void getWithdrawRecord(boolean pullToRefresh, boolean showLoading) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getWithdrawRecord(page)
                .compose(new CommonTransformer<>(this, showLoading))
                .subscribe(new ErrorHandleSubscriber<Optional<WithdrawRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(Optional<WithdrawRecordBean> withdrawRecordBeanOptional) {
                        page++;
                        mRootView.onGetWithdrawRecordSuccess(withdrawRecordBeanOptional.isEmpty() ? null : withdrawRecordBeanOptional.get().getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetWithdrawRecordFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
