package me.jessyan.peach.shop.user.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.ChangeNicknameContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/29/2018 22:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangeNicknamePresenter extends BasePresenter<ChangeNicknameContract.Model, ChangeNicknameContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public ChangeNicknamePresenter(ChangeNicknameContract.Model model, ChangeNicknameContract.View rootView) {
        super(model, rootView);
    }

    public void changeNickname(String nickname) {
        mModel.changeNickname(nickname)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<ResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(ResultBean resultBean) {
                        ToastUtils.showShort(R.string.modify_success);
                        mRootView.onChangeNicknameSuccess(resultBean.getResult());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
