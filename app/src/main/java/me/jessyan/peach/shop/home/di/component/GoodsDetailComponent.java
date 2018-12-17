package me.jessyan.peach.shop.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;
import me.jessyan.peach.shop.home.di.module.GoodsDetailModule;
import me.jessyan.peach.shop.home.mvp.contract.GoodsDetailContract;
import me.jessyan.peach.shop.home.mvp.ui.activity.GoodsDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/16/2018 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GoodsDetailModule.class, dependencies = AppComponent.class)
public interface GoodsDetailComponent {
    void inject(GoodsDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GoodsDetailComponent.Builder view(GoodsDetailContract.View view);

        GoodsDetailComponent.Builder appComponent(AppComponent appComponent);

        GoodsDetailComponent build();
    }
}