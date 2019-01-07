package me.jessyan.peach.shop.user.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.ChangeMobileContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 21:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangeMobilePresenter extends BasePresenter<ChangeMobileContract.Model, ChangeMobileContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public ChangeMobilePresenter(ChangeMobileContract.Model model, ChangeMobileContract.View rootView) {
        super(model, rootView);
    }

    public void getVerifyCode(String mobile) {
        mModel.getVerifyCode(mobile)
                .compose(new CommonTransformer<>(this))
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

    public void verifyMobile(String mobile, String verifyCode) {
        mModel.verifyMobile(mobile, verifyCode)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<ResultBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<ResultBean> resultBeanBasicResponse) {
                        if (resultBeanBasicResponse.getCode() == 0) {
                            //验证成功
                            mRootView.onVerifyMobileSuccess();
                        } else {
                            //验证失败,Toast提示用户
                            String result = resultBeanBasicResponse.getMsg();
                            ToastUtils.showShort(result);
                            mRootView.onVerifyMobileFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onVerifyMobileFailed();
                    }
                });
    }

    public void modifyMobile(String oldMobile, String newMobile, String verifyCode) {
        mModel.modifyMobile(oldMobile, newMobile, verifyCode)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<ResultBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<ResultBean> resultBeanBasicResponse) {
                        if (resultBeanBasicResponse.getCode() == 0) {
                            //修改成功
                            UserInfo.getInstance().setMobile(newMobile);
                            ToastUtils.showShort(R.string.modify_success);
                            mRootView.onModifyMobileSuccess();
                        } else {
                            //修改失败
                            String result = resultBeanBasicResponse.getMsg();
                            ToastUtils.showShort(result);
                            mRootView.onModifyMobileFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onModifyMobileFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
