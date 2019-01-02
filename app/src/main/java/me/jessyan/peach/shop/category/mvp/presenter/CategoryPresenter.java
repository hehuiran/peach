package me.jessyan.peach.shop.category.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.category.mvp.contract.CategoryContract;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CategoryPresenter extends BasePresenter<CategoryContract.Model, CategoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public CategoryPresenter(CategoryContract.Model model, CategoryContract.View rootView) {
        super(model, rootView);
    }

    public void getSubCategoryData(int typeId) {
        mModel.getSubCategoryData(typeId)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<GoodsCategoryGridBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<GoodsCategoryGridBean> basicResponse) {
                        mRootView.onGetSubCategoryDataSuccess(basicResponse.getData() == null ? null : basicResponse.getData().getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetSubCategoryDataFailed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
