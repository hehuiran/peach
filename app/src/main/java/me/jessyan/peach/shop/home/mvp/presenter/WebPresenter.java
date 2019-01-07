package me.jessyan.peach.shop.home.mvp.presenter;

import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.WebShipBean;
import me.jessyan.peach.shop.home.mvp.contract.WebContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/05/2019 21:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WebPresenter extends BasePresenter<WebContract.Model, WebContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public WebPresenter(WebContract.Model model, WebContract.View rootView) {
        super(model, rootView);
    }

    public void getWebShip(String shipId) {
        mModel.getWebShip(shipId)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<WebShipBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<WebShipBean> webShipBeanBasicResponse) {
                        WebShipBean data = webShipBeanBasicResponse.getData();
                        if (webShipBeanBasicResponse.getCode() == 0 && data != null) {
                            if (!TextUtils.isEmpty(data.getMsg()) && !TextUtils.isEmpty(webShipBeanBasicResponse.getMsg())) {
                                mRootView.obtainBeanFail(data.getMsg());
                            } else {
                                mRootView.obtainBeanSuccess(data);
                            }
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
