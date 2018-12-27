package me.jessyan.peach.shop.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.user.di.module.BindMobileModule;
import me.jessyan.peach.shop.user.mvp.contract.BindMobileContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.user.mvp.ui.activity.BindMobileActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 21:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BindMobileModule.class, dependencies = AppComponent.class)
public interface BindMobileComponent {
    void inject(BindMobileActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindMobileComponent.Builder view(BindMobileContract.View view);

        BindMobileComponent.Builder appComponent(AppComponent appComponent);

        BindMobileComponent build();
    }
}