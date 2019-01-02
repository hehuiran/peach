package me.jessyan.peach.shop.home.mvp.presenter;

import android.text.TextUtils;

import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailOptionalBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.help.AliTradeHelper;
import me.jessyan.peach.shop.home.mvp.contract.GoodsDetailContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/16/2018 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.Model, GoodsDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GoodsDetailPresenter(GoodsDetailContract.Model model, GoodsDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getGoodsDetailData(GoodsDetailConfigBean configBean) {
        mModel.getGoodsDetailData(configBean)
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<GoodsDetailOptionalBean>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsDetailOptionalBean goodsDetailOptionalBean) {
                        mRootView.onGetGoodsDetailDataSuccess(goodsDetailOptionalBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onGetGoodsDetailDataFailed();
                    }
                });
    }

    public void collectionGoods(String itemId) {
        mModel.collectionGoods(itemId)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<String> stringBasicResponse) {
                        ToastUtils.showShort(stringBasicResponse.getData());
                        if (stringBasicResponse.getCode() == 0) {
                            mRootView.onGoodsCollectionStateChangeSuccess(true);
                        }
                    }
                });
    }

    public void cancelCollectionGoods(String itemId) {
        mModel.cancelCollectionGoods(itemId)
                .compose(new CommonTransformer<>(this, false))
                .subscribe(new ErrorHandleSubscriber<BasicResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BasicResponse<String> stringBasicResponse) {
                        ToastUtils.showShort(stringBasicResponse.getData());
                        if (stringBasicResponse.getCode() == 0) {
                            mRootView.onGoodsCollectionStateChangeSuccess(false);
                        }
                    }
                });
    }

    public void turnLink(String itemId) {
        mModel.turnLink(itemId, UserInfo.getInstance().getPid())
                .compose(new CommonTransformer<>(this))
                .subscribe(new ErrorHandleSubscriber<Optional<ResultBean>>(mErrorHandler) {
                    @Override
                    public void onNext(Optional<ResultBean> resultBeanOptional) {
                        String link = resultBeanOptional.isEmpty() ? null : resultBeanOptional.get().getResult();
                        if (TextUtils.isEmpty(link)) {
                            String msg = resultBeanOptional.isEmpty() || TextUtils.isEmpty(resultBeanOptional.get().getMsg()) ? ResourceUtils.getResourceString(R.string.net_error) :
                                    resultBeanOptional.get().getMsg();
                            ToastUtils.showShort(msg);
                        } else {
                            AlibcPage page = new AlibcPage(link);
                            AliTradeHelper.getDefault().show(mRootView.getActivity(), page, OpenType.Native);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ToastUtils.showShort(R.string.net_error);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
