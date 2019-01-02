package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.user.InviteFriendBean;
import me.jessyan.peach.shop.mine.mvp.contract.InviteContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 13:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InvitePresenter extends BasePresenter<InviteContract.Model, InviteContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public InvitePresenter(InviteContract.Model model, InviteContract.View rootView) {
        super(model, rootView);
    }

    public void getInviteData() {
        mModel.getInviteData()
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<Optional<InviteFriendBean>>(mErrorHandler) {
                    @Override
                    public void onNext(Optional<InviteFriendBean> inviteFriendBeanOptional) {
                        mRootView.onGetInviteDataSuccess(inviteFriendBeanOptional.isEmpty()?null:inviteFriendBeanOptional.get().getData());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
