package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.MyFansSubModule;
import me.jessyan.peach.shop.mine.mvp.contract.MyFansSubContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.mine.mvp.ui.fragment.MyFansSubFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 19:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyFansSubModule.class, dependencies = AppComponent.class)
public interface MyFansSubComponent {
    void inject(MyFansSubFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyFansSubComponent.Builder view(MyFansSubContract.View view);

        MyFansSubComponent.Builder appComponent(AppComponent appComponent);

        MyFansSubComponent build();
    }
}