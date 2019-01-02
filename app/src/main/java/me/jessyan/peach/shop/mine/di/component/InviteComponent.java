package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.InviteModule;
import me.jessyan.peach.shop.mine.mvp.contract.InviteContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.InviteActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 13:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InviteModule.class, dependencies = AppComponent.class)
public interface InviteComponent {
    void inject(InviteActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InviteComponent.Builder view(InviteContract.View view);

        InviteComponent.Builder appComponent(AppComponent appComponent);

        InviteComponent build();
    }
}