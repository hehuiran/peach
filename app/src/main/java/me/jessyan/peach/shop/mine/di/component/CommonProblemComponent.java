package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.CommonProblemModule;
import me.jessyan.peach.shop.mine.mvp.contract.CommonProblemContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.CommonProblemActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/02/2019 01:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommonProblemModule.class, dependencies = AppComponent.class)
public interface CommonProblemComponent {
    void inject(CommonProblemActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommonProblemComponent.Builder view(CommonProblemContract.View view);

        CommonProblemComponent.Builder appComponent(AppComponent appComponent);

        CommonProblemComponent build();
    }
}