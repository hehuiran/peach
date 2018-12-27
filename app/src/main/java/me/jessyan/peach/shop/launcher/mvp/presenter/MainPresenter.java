package me.jessyan.peach.shop.launcher.mvp.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ConfigBean;
import me.jessyan.peach.shop.launcher.mvp.contract.MainContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/10/2018 21:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void getConfigInfo(boolean isNeedAutoLogin, String token) {
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                Context context = getContext();
                if (context != null && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager telephonyManager =
                            (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        @SuppressLint("HardwareIds") String deviceId = telephonyManager.getDeviceId();
                        getConfigInfo(isNeedAutoLogin, token, deviceId);
                    }
                }
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }

    private void getConfigInfo(boolean isNeedAutoLogin, String token, String deviceId) {
        mModel.getConfigInfo(isNeedAutoLogin, token, deviceId)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<ConfigBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<ConfigBean> configBeanBasicResponse) {
                        mRootView.onConfigSuccess(configBeanBasicResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onConfigFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
