package me.jessyan.peach.shop.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.user.di.module.SettingModule;
import me.jessyan.peach.shop.user.mvp.contract.SettingContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.user.mvp.ui.activity.SettingActivity;


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
@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent {
    void inject(SettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SettingComponent.Builder view(SettingContract.View view);

        SettingComponent.Builder appComponent(AppComponent appComponent);

        SettingComponent build();
    }
}