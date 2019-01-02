package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.WithdrawModule;
import me.jessyan.peach.shop.mine.mvp.contract.WithdrawContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.WithdrawActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 22:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WithdrawModule.class, dependencies = AppComponent.class)
public interface WithdrawComponent {
    void inject(WithdrawActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WithdrawComponent.Builder view(WithdrawContract.View view);

        WithdrawComponent.Builder appComponent(AppComponent appComponent);

        WithdrawComponent build();
    }
}