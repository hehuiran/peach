package me.jessyan.peach.shop.mine.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.mine.di.module.FeedbackModule;
import me.jessyan.peach.shop.mine.mvp.contract.FeedbackContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.mine.mvp.ui.activity.FeedbackActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2019 20:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {
    void inject(FeedbackActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedbackComponent.Builder view(FeedbackContract.View view);

        FeedbackComponent.Builder appComponent(AppComponent appComponent);

        FeedbackComponent build();
    }
}