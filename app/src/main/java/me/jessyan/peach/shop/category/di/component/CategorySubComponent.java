package me.jessyan.peach.shop.category.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.category.di.module.CategorySubModule;
import me.jessyan.peach.shop.category.mvp.contract.CategorySubContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.category.mvp.ui.activity.CategorySubActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 21:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CategorySubModule.class, dependencies = AppComponent.class)
public interface CategorySubComponent {
    void inject(CategorySubActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CategorySubComponent.Builder view(CategorySubContract.View view);

        CategorySubComponent.Builder appComponent(AppComponent appComponent);

        CategorySubComponent build();
    }
}