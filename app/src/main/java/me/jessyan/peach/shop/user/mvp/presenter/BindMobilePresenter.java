package me.jessyan.peach.shop.user.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.HashMap;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.BindMobileContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 21:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BindMobilePresenter extends BasePresenter<BindMobileContract.Model, BindMobileContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public BindMobilePresenter(BindMobileContract.Model model, BindMobileContract.View rootView) {
        super(model, rootView);
    }

    public void getVerifyCode(String mobile) {
        mModel.getVerifyCode(mobile)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<ResultBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<ResultBean> resultBeanBasicResponse) {
                        if (resultBeanBasicResponse.getCode() == 0) {
                            //验证码发送成功
                            mRootView.onGetVerifyCodeSuccess();
                        } else {
                            //验证码发送失败,Toast提示用户
                            String result = resultBeanBasicResponse.getData().getResult();
                            ToastUtils.showShort(result);
                            mRootView.onGetVerifyCodeFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetVerifyCodeFailed();
                    }
                });
    }

    public void bindMobile(HashMap<String, Object> map) {
        mModel.bindMobile(map)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<LoginBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<LoginBean> loginBeanBasicResponse) {
                        mRootView.onBindMobileSuccess(loginBeanBasicResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onBindMobileFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
