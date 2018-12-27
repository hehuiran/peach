package me.jessyan.peach.shop.dynamic.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.dynamic.di.module.DynamicSubModule;
import me.jessyan.peach.shop.dynamic.mvp.contract.DynamicSubContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.dynamic.mvp.ui.fragment.DynamicSubFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2018 23:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DynamicSubModule.class, dependencies = AppComponent.class)
public interface DynamicSubComponent {
    void inject(DynamicSubFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DynamicSubComponent.Builder view(DynamicSubContract.View view);

        DynamicSubComponent.Builder appComponent(AppComponent appComponent);

        DynamicSubComponent build();
    }
}