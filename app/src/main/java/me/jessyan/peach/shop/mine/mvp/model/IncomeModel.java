package me.jessyan.peach.shop.mine.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.mine.IncomeReportsDetailsBean;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeContract;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.MineApiService;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 14:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class IncomeModel extends BaseModel implements IncomeContract.Model {

    @Inject
    public IncomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<IncomeReportsDetailsBean> getIncomeReportsDetails(String shopType) {
        return mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .getIncomeReportsDetails(shopType)
                .map(new ResponseFunction<>(IncomeReportsDetailsBean.class));
    }
}