package me.jessyan.peach.shop.user.mvp.presenter;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.user.mvp.contract.SettingContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/28/2018 22:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SettingPresenter extends BasePresenter<SettingContract.Model, SettingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public SettingPresenter(SettingContract.Model model, SettingContract.View rootView) {
        super(model, rootView);
    }

    public void changeAvatarImage(String originalPath) {
        mModel.changeAvatarImage(originalPath)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<ResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(ResultBean resultBean) {
                        mRootView.onChangeAvatarImageSuccess(resultBean.getResult());
                    }
                });
    }

    public void logoutAlibc() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (alibcLogin.isLogin()) {
            alibcLogin.logout(new AlibcLoginCallback() {
                @Override
                public void onSuccess(int i) {

                }

                @Override
                public void onFailure(int i, String s) {

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
