package me.jessyan.peach.shop.mine.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import me.jessyan.peach.shop.mine.mvp.contract.OrderSubContract;
import me.jessyan.peach.shop.mine.mvp.model.OrderSubModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class OrderSubModule {

    @Binds
    abstract OrderSubContract.Model bindOrderSubModel(OrderSubModel model);
}