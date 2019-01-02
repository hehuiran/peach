package me.jessyan.peach.shop.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.user.di.module.ChangeNicknameModule;
import me.jessyan.peach.shop.user.mvp.contract.ChangeNicknameContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.user.mvp.ui.activity.ChangeNicknameActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/29/2018 22:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ChangeNicknameModule.class, dependencies = AppComponent.class)
public interface ChangeNicknameComponent {
    void inject(ChangeNicknameActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChangeNicknameComponent.Builder view(ChangeNicknameContract.View view);

        ChangeNicknameComponent.Builder appComponent(AppComponent appComponent);

        ChangeNicknameComponent build();
    }
}