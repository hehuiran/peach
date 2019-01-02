package me.jessyan.peach.shop.mine.mvp.model;


import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.IncomeReportsListBean;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeDetailContract;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.MineApiService;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 18:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class IncomeDetailModel extends BaseModel implements IncomeDetailContract.Model {

    @Inject
    public IncomeDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<IncomeReportsListBean> getIncomeDetail(int page, int type) {
        return mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .getIncomeReportsListData(page, CommonConstant.PAGE_SIZE, type)
                .map(new ResponseFunction<>(IncomeReportsListBean.class));
    }
}