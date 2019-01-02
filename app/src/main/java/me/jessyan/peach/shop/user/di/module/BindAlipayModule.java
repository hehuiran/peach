package me.jessyan.peach.shop.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import me.jessyan.peach.shop.user.mvp.contract.BindAlipayContract;
import me.jessyan.peach.shop.user.mvp.model.BindAlipayModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class BindAlipayModule {

    @Binds
    abstract BindAlipayContract.Model bindBindAlipayModel(BindAlipayModel model);
}