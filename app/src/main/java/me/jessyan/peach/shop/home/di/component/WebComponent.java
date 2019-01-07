package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.WebModule;
import me.jessyan.peach.shop.home.mvp.contract.WebContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.home.mvp.ui.activity.WebActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/05/2019 21:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WebModule.class, dependencies = AppComponent.class)
public interface WebComponent {
    void inject(WebActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WebComponent.Builder view(WebContract.View view);

        WebComponent.Builder appComponent(AppComponent appComponent);

        WebComponent build();
    }
}