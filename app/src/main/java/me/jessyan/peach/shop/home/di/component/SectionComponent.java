package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.SectionModule;
import me.jessyan.peach.shop.home.mvp.contract.SectionContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.home.mvp.ui.activity.SectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2019 15:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SectionModule.class, dependencies = AppComponent.class)
public interface SectionComponent {
    void inject(SectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SectionComponent.Builder view(SectionContract.View view);

        SectionComponent.Builder appComponent(AppComponent appComponent);

        SectionComponent build();
    }
}