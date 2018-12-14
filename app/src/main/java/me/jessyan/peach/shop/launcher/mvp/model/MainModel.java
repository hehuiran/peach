package me.jessyan.peach.shop.launcher.mvp.model;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.constant.SPKey;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ConfigBean;
import me.jessyan.peach.shop.entity.ShareBean;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.launcher.mvp.contract.MainContract;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.netconfig.temporary.WillBuyApiService;


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
public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BasicResponse<ConfigBean>> getConfigInfo(boolean isNeedAutoLogin, String token, String deviceId) {
        Observable<BasicResponse<ConfigBean>> responseObservable;
        if (isNeedAutoLogin) {
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = "unknown";
            }
            responseObservable = mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                    .autoLogin(token, deviceId)
                    .flatMap((Function<BasicResponse<LoginBean>, ObservableSource<BasicResponse<ConfigBean>>>) loginBeanBasicResponse -> {
                        if (loginBeanBasicResponse.getCode() == 0) {
                            //登录成功，保存用户信息
                            LoginBean loginBean = loginBeanBasicResponse.getData();

                            UserInfo.getInstance().setData(loginBean);

                            //获取配置信息
                            return getConfigObservable();
                        }
                        BasicResponse<ConfigBean> basicResponse = new BasicResponse<>();
                        basicResponse.setCode(loginBeanBasicResponse.getCode());
                        basicResponse.setMsg(loginBeanBasicResponse.getMsg());
                        return Observable.just(basicResponse);
                    });
        } else {
            responseObservable = getConfigObservable();
        }
        return responseObservable;
    }

    /**
     * 获取配置信息
     */
    private Observable<BasicResponse<ConfigBean>> getConfigObservable() {
        Observable<BasicResponse<ShareBean>> shareObservable = mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getShareInfo(System.currentTimeMillis());

        Observable<BasicResponse<ConfigBean>> configObservable = mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class).getConfig();

        return Observable.zip(shareObservable, configObservable,
                (beanBasicResponse, configBeanBasicResponse) -> {
                    ShareBean shareModel = beanBasicResponse.getData();
                    if (shareModel != null && beanBasicResponse.getCode() == 0) {
                        ShareBean.WxShareModel wxShareModel = shareModel.getWxShareModel();
                        SPUtils.getInstance().put(SPKey.WX_SHARE_ICON, wxShareModel.getIcon());
                        SPUtils.getInstance().put(SPKey.WX_SHARE_TITLE, wxShareModel.getTitle());
                        SPUtils.getInstance().put(SPKey.WX_SHARE_URL, wxShareModel.getUrl());
                    }
                    return configBeanBasicResponse;
                });
    }
}