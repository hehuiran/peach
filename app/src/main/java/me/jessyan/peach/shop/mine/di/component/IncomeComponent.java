package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.IncomeModule;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.mine.mvp.ui.fragment.IncomeFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 14:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = IncomeModule.class, dependencies = AppComponent.class)
public interface IncomeComponent {
    void inject(IncomeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IncomeComponent.Builder view(IncomeContract.View view);

        IncomeComponent.Builder appComponent(AppComponent appComponent);

        IncomeComponent build();
    }
}