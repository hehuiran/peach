package me.jessyan.peach.shop.user.di.module;

import dagger.Binds;
import dagger.Module;

import me.jessyan.peach.shop.user.mvp.contract.SettingContract;
import me.jessyan.peach.shop.user.mvp.model.SettingModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/28/2018 22:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SettingModule {

    @Binds
    abstract SettingContract.Model bindSettingModel(SettingModel model);
}