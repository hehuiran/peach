package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.mine.IncomeReportsDetailsBean;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 14:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class IncomePresenter extends BasePresenter<IncomeContract.Model, IncomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public IncomePresenter(IncomeContract.Model model, IncomeContract.View rootView) {
        super(model, rootView);
    }

    public void getIncomeReportsDetails(String shopType) {
        mModel.getIncomeReportsDetails(shopType)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<IncomeReportsDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(IncomeReportsDetailsBean incomeReportsDetailsBean) {
                        List<IncomeReportsDetailsBean.DataBean> data = incomeReportsDetailsBean.getData();
                        if (data != null && !data.isEmpty()) {
                            mRootView.onGetIncomeReportsDetailsSuccess(data.get(0));
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
