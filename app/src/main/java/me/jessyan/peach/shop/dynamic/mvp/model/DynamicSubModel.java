package me.jessyan.peach.shop.dynamic.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.dynamic.mvp.contract.DynamicSubContract;
import me.jessyan.peach.shop.entity.DynamicBean;
import me.jessyan.peach.shop.entity.SuccessBean;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.NewApi;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2018 23:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class DynamicSubModel extends BaseModel implements DynamicSubContract.Model {

    @Inject
    public DynamicSubModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DynamicBean> getDynamicData(int type, int page) {
        return mRepositoryManager.obtainRetrofitService(NewApi.class)
                .getDynamicData(type, page, CommonConstant.DYNAMIC_PAGE_SIZE)
                .map(new ResponseFunction<>(DynamicBean.class));
    }

    @Override
    public Observable<Optional<SuccessBean>> addShareNum(String id) {
        return mRepositoryManager.obtainRetrofitService(NewApi.class)
                .addShareNum(id)
                .compose(RxTransformer.handleResponse());
    }
}