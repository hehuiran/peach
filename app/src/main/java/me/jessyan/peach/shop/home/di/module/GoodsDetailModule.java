package me.jessyan.peach.shop.home.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import me.jessyan.peach.shop.home.mvp.contract.GoodsDetailContract;
import me.jessyan.peach.shop.home.mvp.model.GoodsDetailModel;


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
@Module
public abstract class GoodsDetailModule {

    @Binds
    abstract GoodsDetailContract.Model bindGoodsDetailModel(GoodsDetailModel model);
}