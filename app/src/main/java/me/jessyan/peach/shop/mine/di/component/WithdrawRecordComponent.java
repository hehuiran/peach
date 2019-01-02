package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.WithdrawRecordModule;
import me.jessyan.peach.shop.mine.mvp.contract.WithdrawRecordContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.WithdrawRecordActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 12:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WithdrawRecordModule.class, dependencies = AppComponent.class)
public interface WithdrawRecordComponent {
    void inject(WithdrawRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WithdrawRecordComponent.Builder view(WithdrawRecordContract.View view);

        WithdrawRecordComponent.Builder appComponent(AppComponent appComponent);

        WithdrawRecordComponent build();
    }
}