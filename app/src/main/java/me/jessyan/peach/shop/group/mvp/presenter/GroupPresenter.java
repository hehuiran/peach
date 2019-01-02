package me.jessyan.peach.shop.group.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.group.GroupOptionalBean;
import me.jessyan.peach.shop.group.mvp.contract.GroupContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class GroupPresenter extends BasePresenter<GroupContract.Model, GroupContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;

    @Inject
    public GroupPresenter(GroupContract.Model model, GroupContract.View rootView) {
        super(model, rootView);
    }

    public void getGroupData(boolean pullToRefresh, boolean showLoading) {
        if (pullToRefresh) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getGroupData(page, pullToRefresh)
                .compose(new CommonTransformer<>(this, showLoading))
                .subscribe(new ErrorHandleSubscriber<GroupOptionalBean>(mErrorHandler) {
                    @Override
                    public void onNext(GroupOptionalBean optionalBean) {
                        page++;
                        mRootView.onGetGroupDataSuccess(optionalBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetGroupDataFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
