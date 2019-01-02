package me.jessyan.peach.shop.mine.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.mine.WithDrawBean;
import me.jessyan.peach.shop.mine.mvp.contract.WithdrawContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.temporary.MineApiService;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 22:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WithdrawModel extends BaseModel implements WithdrawContract.Model {

    @Inject
    public WithdrawModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BasicResponse<ResultBean>> getVerifyCode(String mobile) {
        return mRepositoryManager
                .obtainRetrofitService(PersonalApiService.class)
                .getCode(mobile);
    }

    @Override
    public Observable<Optional<WithDrawBean>> getBalance() {
        return mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .getBalance(System.currentTimeMillis())
                .compose(RxTransformer.handleResponse());
    }

    @Override
    public Observable<Optional<String>> withdraw(String money, String mobile, String verifyCode) {
        return mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .withDraw(money, 1, mobile, verifyCode)
                .compose(RxTransformer.handleResponse());
    }
}