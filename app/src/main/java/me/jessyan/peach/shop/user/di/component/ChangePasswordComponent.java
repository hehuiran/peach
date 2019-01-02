package me.jessyan.peach.shop.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.user.di.module.ChangePasswordModule;
import me.jessyan.peach.shop.user.mvp.contract.ChangePasswordContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.user.mvp.ui.activity.ChangePasswordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChangePasswordModule.class, dependencies = AppComponent.class)
public interface ChangePasswordComponent {
    void inject(ChangePasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChangePasswordComponent.Builder view(ChangePasswordContract.View view);

        ChangePasswordComponent.Builder appComponent(AppComponent appComponent);

        ChangePasswordComponent build();
    }
}