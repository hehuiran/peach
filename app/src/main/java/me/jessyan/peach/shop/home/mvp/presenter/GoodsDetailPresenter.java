package me.jessyan.peach.shop.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailOptionalBean;
import me.jessyan.peach.shop.home.mvp.contract.GoodsDetailContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/16/2018 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.Model, GoodsDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GoodsDetailPresenter(GoodsDetailContract.Model model, GoodsDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getGoodsDetailData(GoodsDetailConfigBean configBean){
        mModel.getGoodsDetailData(configBean)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<GoodsDetailOptionalBean>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsDetailOptionalBean goodsDetailOptionalBean) {
                        mRootView.onGetGoodsDetailDataSuccess(goodsDetailOptionalBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetGoodsDetailDataFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
