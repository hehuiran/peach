package me.jessyan.peach.shop.launcher.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.launcher.mvp.contract.SplashContract;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.server.ConfigService;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/08/2018 21:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model {

    @Inject
    public SplashModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<CouponsBannerBean> getAdvertising(String type) {
        return mRepositoryManager.obtainRetrofitService(ConfigService.class)
                .getBanner(type)
                .map(new ResponseFunction<>(CouponsBannerBean.class));
    }
}