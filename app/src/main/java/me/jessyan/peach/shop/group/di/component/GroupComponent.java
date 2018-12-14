package me.jessyan.peach.shop.group.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.group.di.module.GroupModule;
import me.jessyan.peach.shop.group.mvp.contract.GroupContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.group.mvp.ui.fragment.GroupFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = GroupModule.class, dependencies = AppComponent.class)
public interface GroupComponent {
    void inject(GroupFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupComponent.Builder view(GroupContract.View view);

        GroupComponent.Builder appComponent(AppComponent appComponent);

        GroupComponent build();
    }
}