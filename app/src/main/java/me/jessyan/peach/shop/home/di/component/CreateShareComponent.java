package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.CreateShareModule;
import me.jessyan.peach.shop.home.mvp.contract.CreateShareContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.home.mvp.ui.activity.CreateShareActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/22/2018 18:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CreateShareModule.class, dependencies = AppComponent.class)
public interface CreateShareComponent {
    void inject(CreateShareActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CreateShareComponent.Builder view(CreateShareContract.View view);

        CreateShareComponent.Builder appComponent(AppComponent appComponent);

        CreateShareComponent build();
    }
}