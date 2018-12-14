package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.HomeMainModule;
import me.jessyan.peach.shop.home.mvp.contract.HomeMainContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.home.mvp.ui.fragment.HomeMainFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeMainModule.class, dependencies = AppComponent.class)
public interface HomeMainComponent {
    void inject(HomeMainFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeMainComponent.Builder view(HomeMainContract.View view);

        HomeMainComponent.Builder appComponent(AppComponent appComponent);

        HomeMainComponent build();
    }
}