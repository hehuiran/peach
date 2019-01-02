package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.ServiceProviderModule;
import me.jessyan.peach.shop.mine.mvp.contract.ServiceProviderContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.ServiceProviderActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/01/2019 22:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ServiceProviderModule.class, dependencies = AppComponent.class)
public interface ServiceProviderComponent {
    void inject(ServiceProviderActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ServiceProviderComponent.Builder view(ServiceProviderContract.View view);

        ServiceProviderComponent.Builder appComponent(AppComponent appComponent);

        ServiceProviderComponent build();
    }
}