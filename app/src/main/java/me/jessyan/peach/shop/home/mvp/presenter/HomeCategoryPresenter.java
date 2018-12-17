package me.jessyan.peach.shop.home.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.HomeCategoryOptionalBean;
import me.jessyan.peach.shop.home.mvp.contract.HomeCategoryContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeCategoryPresenter extends BasePresenter<HomeCategoryContract.Model, HomeCategoryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    private int page = CommonConstant.PAGE_INITIAL;
    private String dataTimeStamp = CommonConstant.EMPTY_STRING;

    @Inject
    public HomeCategoryPresenter(HomeCategoryContract.Model model, HomeCategoryContract.View rootView) {
        super(model, rootView);
    }

    public void getHomeCategoryData(int typeId, String oneType,
                                    String twoType, int selectType,
                                    String sort) {

        mModel.getHomeCategoryData(typeId, oneType, twoType, selectType, sort)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<HomeCategoryOptionalBean>(mErrorHandler) {
                    @Override
                    public void onNext(HomeCategoryOptionalBean homeCategoryOptionalBean) {
                        page++;
                        dataTimeStamp = homeCategoryOptionalBean.getCouponsCommodityBean().getDataTimestamp();
                        mRootView.onGetHomeCategoryDataSuccess(homeCategoryOptionalBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetHomeCategoryDataFailed();
                    }
                });
    }

    public void getGoods(String oneType, String twoType,
                         int selectType, String sort, boolean isLoadMore) {
        if (!isLoadMore) {
            page = CommonConstant.PAGE_INITIAL;
        }
        mModel.getGoods(page, oneType, twoType, selectType, sort, dataTimeStamp)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<CouponsCommodityBean>(mErrorHandler) {
                    @Override
                    public void onNext(CouponsCommodityBean couponsCommodityBean) {
                        if (isLoadMore) {
                            page++;
                            mRootView.onLoadMoreGoodsSuccess(couponsCommodityBean.getList());
                        } else {
                            mRootView.onGoodsRefreshCompleted(couponsCommodityBean.getList());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (isLoadMore) {
                            mRootView.onLoadMoreGoodsFailed();
                        } else {
                            List<GoodsBean> list = new ArrayList<>();
                            GoodsBean bean = new GoodsBean();
                            bean.setItemType(RecyclerViewType.NET_ERROR_TYPE);
                            list.add(bean);
                            mRootView.onGoodsRefreshCompleted(list);
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
