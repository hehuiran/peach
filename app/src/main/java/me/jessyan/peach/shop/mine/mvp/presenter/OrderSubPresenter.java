package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.OrderSubBean;
import me.jessyan.peach.shop.mine.mvp.contract.OrderSubContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class OrderSubPresenter extends BasePresenter<OrderSubContract.Model, OrderSubContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public OrderSubPresenter(OrderSubContract.Model model, OrderSubContract.View rootView) {
        super(model, rootView);
    }

    public void getOrderDetail(int type, boolean pullToRefresh, boolean showLoading) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getOrderDetail(type, page)
                .compose(new CommonTransformer<>(this, showLoading))
                .subscribe(new ErrorHandleSubscriber<Optional<OrderSubBean>>(mErrorHandler) {
                    @Override
                    public void onNext(Optional<OrderSubBean> orderSubBeanOptional) {
                        mRootView.onGetOrderDetailSuccess(orderSubBeanOptional.isEmpty() ? null : orderSubBeanOptional.get().getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetOrderDetailFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
