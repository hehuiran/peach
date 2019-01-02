package me.jessyan.peach.shop.user.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;
import me.jessyan.peach.shop.user.mvp.contract.BindAlipayContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BindAlipayModel extends BaseModel implements BindAlipayContract.Model {

    @Inject
    public BindAlipayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BasicResponse<ResultBean>> getVerifyCode(String mobile) {
        return mRepositoryManager
                .obtainRetrofitService(PersonalApiService.class)
                .getCode(mobile);
    }

    @Override
    public Observable<Optional<String>> bindAlipay(String alipay, String realName, String verifyCode) {
        return mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                .modifyAlipay(alipay,realName)
                .compose(RxTransformer.handleResponse());
    }
}