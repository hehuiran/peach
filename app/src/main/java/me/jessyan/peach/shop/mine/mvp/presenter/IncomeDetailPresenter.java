package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.IncomeReportsListBean;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeDetailContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 18:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class IncomeDetailPresenter extends BasePresenter<IncomeDetailContract.Model, IncomeDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public IncomeDetailPresenter(IncomeDetailContract.Model model, IncomeDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getIncomeDetail(int type, boolean pullToRefresh, boolean showLoading) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getIncomeDetail(page, type)
                .compose(new CommonTransformer<>(this, showLoading))
                .subscribe(new ErrorHandleSubscriber<IncomeReportsListBean>(mErrorHandler) {
                    @Override
                    public void onNext(IncomeReportsListBean incomeReportsListBean) {
                        page++;
                        mRootView.onGetIncomeDetailSuccess(incomeReportsListBean.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetIncomeDetailFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
