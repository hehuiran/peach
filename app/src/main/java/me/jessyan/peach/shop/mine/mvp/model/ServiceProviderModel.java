package me.jessyan.peach.shop.mine.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import me.jessyan.peach.shop.mine.mvp.contract.ServiceProviderContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/01/2019 22:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ServiceProviderModel extends BaseModel implements ServiceProviderContract.Model {

    @Inject
    public ServiceProviderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}