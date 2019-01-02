package me.jessyan.peach.shop.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.user.di.module.BindAlipayModule;
import me.jessyan.peach.shop.user.mvp.contract.BindAlipayContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.user.mvp.ui.activity.BindAlipayActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BindAlipayModule.class, dependencies = AppComponent.class)
public interface BindAlipayComponent {
    void inject(BindAlipayActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindAlipayComponent.Builder view(BindAlipayContract.View view);

        BindAlipayComponent.Builder appComponent(AppComponent appComponent);

        BindAlipayComponent build();
    }
}