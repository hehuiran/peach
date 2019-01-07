package me.jessyan.peach.shop.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.user.di.module.ChangeMobileModule;
import me.jessyan.peach.shop.user.mvp.contract.ChangeMobileContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.user.mvp.ui.activity.ChangeMobileActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 21:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChangeMobileModule.class, dependencies = AppComponent.class)
public interface ChangeMobileComponent {
    void inject(ChangeMobileActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChangeMobileComponent.Builder view(ChangeMobileContract.View view);

        ChangeMobileComponent.Builder appComponent(AppComponent appComponent);

        ChangeMobileComponent build();
    }
}