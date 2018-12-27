package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.SearchResultModule;
import me.jessyan.peach.shop.home.mvp.contract.SearchResultContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.home.mvp.ui.activity.SearchResultActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/22/2018 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SearchResultModule.class, dependencies = AppComponent.class)
public interface SearchResultComponent {
    void inject(SearchResultActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchResultComponent.Builder view(SearchResultContract.View view);

        SearchResultComponent.Builder appComponent(AppComponent appComponent);

        SearchResultComponent build();
    }
}