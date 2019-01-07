package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.PlatformNoticeModule;
import me.jessyan.peach.shop.mine.mvp.contract.PlatformNoticeContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.PlatformNoticeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2019 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PlatformNoticeModule.class, dependencies = AppComponent.class)
public interface PlatformNoticeComponent {
    void inject(PlatformNoticeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PlatformNoticeComponent.Builder view(PlatformNoticeContract.View view);

        PlatformNoticeComponent.Builder appComponent(AppComponent appComponent);

        PlatformNoticeComponent build();
    }
}