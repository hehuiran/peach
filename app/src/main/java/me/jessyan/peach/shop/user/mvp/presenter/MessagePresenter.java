package me.jessyan.peach.shop.user.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.MessageBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.user.mvp.contract.MessageContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 23:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public MessagePresenter(MessageContract.Model model, MessageContract.View rootView) {
        super(model, rootView);
    }

    public void getMessageList(boolean pullToRefresh) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getMessageList(page)
                .compose(new CommonTransformer<>(this, pullToRefresh))
                .subscribe(new ErrorHandleSubscriber<MessageBean>(mErrorHandler) {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        page++;
                        mRootView.onGetMessageListSuccess(messageBean.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetMessageListFailed();
                    }
                });
    }

    public void deleteMessage(String id, int position) {
        mModel.deleteMessage(id)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<String> stringBasicResponse) {
                        if (stringBasicResponse.getCode() == 0) {
                            mRootView.onDeleteMessageSuccess(position);
                        } else {
                            mRootView.onDeleteMessageFailed(position);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDeleteMessageFailed(position);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
