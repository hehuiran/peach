package me.jessyan.peach.shop.mine.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.WithdrawRecordBean;
import me.jessyan.peach.shop.mine.mvp.contract.WithdrawRecordContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.temporary.MineApiService;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 12:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WithdrawRecordModel extends BaseModel implements WithdrawRecordContract.Model {

    @Inject
    public WithdrawRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Optional<WithdrawRecordBean>> getWithdrawRecord(int page) {
        return mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .getWithDrawRecord(CommonConstant.EMPTY_STRING, page, CommonConstant.PAGE_SIZE)
                .compose(RxTransformer.handleResponse());
    }
}