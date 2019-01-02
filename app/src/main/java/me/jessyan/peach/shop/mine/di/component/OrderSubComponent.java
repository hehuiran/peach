package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.OrderSubModule;
import me.jessyan.peach.shop.mine.mvp.contract.OrderSubContract;

import com.jess.arms.di.scope.FragmentScope;

import me.jessyan.peach.shop.mine.mvp.ui.fragment.OrderSubFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OrderSubModule.class, dependencies = AppComponent.class)
public interface OrderSubComponent {
    void inject(OrderSubFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OrderSubComponent.Builder view(OrderSubContract.View view);

        OrderSubComponent.Builder appComponent(AppComponent appComponent);

        OrderSubComponent build();
    }
}