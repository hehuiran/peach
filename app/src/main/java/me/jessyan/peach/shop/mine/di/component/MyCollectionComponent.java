package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.MyCollectionModule;
import me.jessyan.peach.shop.mine.mvp.contract.MyCollectionContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.MyCollectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 22:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyCollectionModule.class, dependencies = AppComponent.class)
public interface MyCollectionComponent {
    void inject(MyCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCollectionComponent.Builder view(MyCollectionContract.View view);

        MyCollectionComponent.Builder appComponent(AppComponent appComponent);

        MyCollectionComponent build();
    }
}