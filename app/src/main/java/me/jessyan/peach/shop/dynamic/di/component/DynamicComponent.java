package me.jessyan.peach.shop.dynamic.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.dynamic.di.module.DynamicModule;
import me.jessyan.peach.shop.dynamic.mvp.contract.DynamicContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.dynamic.mvp.ui.fragment.DynamicFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DynamicModule.class, dependencies = AppComponent.class)
public interface DynamicComponent {
    void inject(DynamicFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DynamicComponent.Builder view(DynamicContract.View view);

        DynamicComponent.Builder appComponent(AppComponent appComponent);

        DynamicComponent build();
    }
}