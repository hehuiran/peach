package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.HomeCategoryModule;
import me.jessyan.peach.shop.home.mvp.contract.HomeCategoryContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.home.mvp.ui.fragment.HomeCategoryFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeCategoryModule.class, dependencies = AppComponent.class)
public interface HomeCategoryComponent {
    void inject(HomeCategoryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeCategoryComponent.Builder view(HomeCategoryContract.View view);

        HomeCategoryComponent.Builder appComponent(AppComponent appComponent);

        HomeCategoryComponent build();
    }
}