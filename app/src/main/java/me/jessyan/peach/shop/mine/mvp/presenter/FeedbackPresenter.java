package me.jessyan.peach.shop.mine.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.mine.mvp.contract.FeedbackContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2019 20:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model, FeedbackContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public FeedbackPresenter(FeedbackContract.Model model, FeedbackContract.View rootView) {
        super(model, rootView);
    }

    public void feedback(String content) {
        mModel.feedback(content)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<String> stringBasicResponse) {
                        if (stringBasicResponse.getCode() == 0) {
                            mRootView.onFeedbackSuccess();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
