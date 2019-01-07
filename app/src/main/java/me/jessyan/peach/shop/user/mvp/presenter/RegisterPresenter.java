package me.jessyan.peach.shop.user.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.RegisterContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/18/2018 23:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView) {
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

    public void readPhoneState() {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.readPhoneStateSuccess();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                ToastUtils.showShort(R.string.request_permissions_failure);
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                ToastUtils.showShort(R.string.need_to_go_to_the_settings);
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }

    public void registerAccount(HashMap<String, Object> map) {
        mModel.registerAccount(map)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<LoginBean>(mErrorHandler) {
                    @Override
                    public void onNext(LoginBean bean) {
                        mRootView.onRegisterAccountSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onRegisterAccountFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
