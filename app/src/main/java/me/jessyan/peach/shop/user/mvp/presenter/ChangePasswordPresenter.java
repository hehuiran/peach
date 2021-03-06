package me.jessyan.peach.shop.user.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.ChangePasswordContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.Model, ChangePasswordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public ChangePasswordPresenter(ChangePasswordContract.Model model, ChangePasswordContract.View rootView) {
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

    public void changePassword(String mobile, String verifyCode, String password){
        mModel.changePassword(mobile, verifyCode, password)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<ResultBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<ResultBean> resultBeanBasicResponse) {
                        if (resultBeanBasicResponse.getCode() == 0) {
                            //修改成功
                            ToastUtils.showShort(R.string.modify_success);
                            mRootView.onChangePasswordSuccess();
                        } else {
                            //修改失败,Toast提示用户
                            String result = resultBeanBasicResponse.getMsg();
                            ToastUtils.showShort(result);
                            mRootView.onChangePasswordFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onChangePasswordFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
