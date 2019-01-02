package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.IncomeDetailModule;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeDetailContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.mine.mvp.ui.fragment.IncomeDetailFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 18:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = IncomeDetailModule.class, dependencies = AppComponent.class)
public interface IncomeDetailComponent {
    void inject(IncomeDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IncomeDetailComponent.Builder view(IncomeDetailContract.View view);

        IncomeDetailComponent.Builder appComponent(AppComponent appComponent);

        IncomeDetailComponent build();
    }
}