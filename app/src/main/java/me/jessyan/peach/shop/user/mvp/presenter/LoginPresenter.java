package me.jessyan.peach.shop.user.mvp.presenter;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;

import java.util.HashMap;

import javax.inject.Inject;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.LoginContract;
import me.jessyan.peach.shop.user.mvp.ui.activity.LoginActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2018 16:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void readPhoneState(@LoginActivity.LoginChannel int loginChannel) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.readPhoneStateSuccess(loginChannel);
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }

    public void mobileLogin(HashMap<String, Object> map) {
        mModel.mobileLogin(map)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<LoginBean>(mErrorHandler) {
                    @Override
                    public void onNext(LoginBean bean) {
                        mRootView.onMobileLoginSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onMobileLoginFailed();
                    }
                });
    }

    public void thirdPartyLogin(HashMap<String, Object> map) {
        mModel.thirdPartyLogin(map)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<LoginBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<LoginBean> loginBeanBasicResponse) {
                        hideLoading();
                        mRootView.onThirdPartyLoginSuccess(loginBeanBasicResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        hideLoading();
                    }
                });
    }

    public void aliLogin() {
        AlibcLogin instance = AlibcLogin.getInstance();
        if (instance.isLogin()) {
            mRootView.onAliLoginSuccess();
        } else {
            instance.showLogin(new AlibcLoginCallback() {
                @Override
                public void onSuccess(int i) {
                    LogUtils.d(TAG, "淘宝授权成功");
                    mRootView.onAliLoginSuccess();
                }

                @Override
                public void onFailure(int i, String s) {
                    LogUtils.d(TAG, "淘宝授权失败i=" + i + "->s=" + s);
                    ToastUtils.showShort(R.string.tb_auth_failed);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
