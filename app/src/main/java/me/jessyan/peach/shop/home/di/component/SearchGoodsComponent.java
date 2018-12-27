package me.jessyan.peach.shop.home.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.home.di.module.SearchGoodsModule;
import me.jessyan.peach.shop.home.mvp.contract.SearchGoodsContract;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.peach.shop.home.mvp.ui.activity.SearchGoodsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SearchGoodsModule.class, dependencies = AppComponent.class)
public interface SearchGoodsComponent {
    void inject(SearchGoodsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchGoodsComponent.Builder view(SearchGoodsContract.View view);

        SearchGoodsComponent.Builder appComponent(AppComponent appComponent);

        SearchGoodsComponent build();
    }
}