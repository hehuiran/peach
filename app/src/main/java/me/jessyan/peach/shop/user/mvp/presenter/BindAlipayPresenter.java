package me.jessyan.peach.shop.user.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.BindAlipayContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BindAlipayPresenter extends BasePresenter<BindAlipayContract.Model, BindAlipayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public BindAlipayPresenter(BindAlipayContract.Model model, BindAlipayContract.View rootView) {
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
                            String result = resultBeanBasicResponse.getMsg();
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

    public void bindAlipay(String alipay, String realName, String verifyCode){
        mModel.bindAlipay(alipay, realName, verifyCode)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<Optional<String>>(mErrorHandler) {
                    @Override
                    public void onNext(Optional<String> stringOptional) {
                        mRootView.onBindAlipaySuccess();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
