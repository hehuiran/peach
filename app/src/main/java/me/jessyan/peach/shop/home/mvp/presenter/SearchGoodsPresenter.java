package me.jessyan.peach.shop.home.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.peach.shop.entity.search.SearchOptionalBean;
import me.jessyan.peach.shop.entity.search.SearchRecordBean;
import me.jessyan.peach.shop.home.mvp.contract.SearchGoodsContract;
import me.jessyan.peach.shop.netconfig.transformer.CommonTransformer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchGoodsPresenter extends BasePresenter<SearchGoodsContract.Model, SearchGoodsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public SearchGoodsPresenter(SearchGoodsContract.Model model, SearchGoodsContract.View rootView) {
        super(model, rootView);
    }

    public void getSearchData(){
        mModel.getSearchData()
                .compose(new CommonTransformer<>(this,false))
                .subscribe(new ErrorHandleSubscriber<SearchOptionalBean>(mErrorHandler) {
                    @Override
                    public void onNext(SearchOptionalBean searchOptionalBean) {
                        mRootView.onGetSearchDataSuccess(searchOptionalBean);
                    }
                });
    }

    public void saveSearchRecord(String value){
        mModel.saveSearchRecord(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<SearchRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(List<SearchRecordBean> searchRecordBeans) {
                        mRootView.onSaveSearchRecordSuccess(searchRecordBeans);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
