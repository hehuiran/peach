package me.jessyan.peach.shop.mine.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import me.jessyan.peach.shop.mine.mvp.contract.CommonProblemContract;


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
public class CommonProblemModel extends BaseModel implements CommonProblemContract.Model {

    @Inject
    public CommonProblemModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}