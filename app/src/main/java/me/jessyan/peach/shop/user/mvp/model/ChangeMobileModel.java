package me.jessyan.peach.shop.user.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.user.mvp.contract.ChangeMobileContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 21:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangeMobileModel extends BaseModel implements ChangeMobileContract.Model {

    @Inject
    public ChangeMobileModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BasicResponse<ResultBean>> getVerifyCode(String mobile) {
        return mRepositoryManager
                .obtainRetrofitService(PersonalApiService.class)
                .getCode(mobile);
    }

    @Override
    public Observable<BasicResponse<ResultBean>> verifyMobile(String mobile, String verifyCode) {
        return mRepositoryManager
                .obtainRetrofitService(PersonalApiService.class)
                .verifyMobile(mobile, verifyCode);
    }

    @Override
    public Observable<BasicResponse<ResultBean>> modifyMobile(String oldMobile, String newMobile, String verifyCode) {
        return mRepositoryManager
                .obtainRetrofitService(PersonalApiService.class)
                .modifyMobile(oldMobile, newMobile, verifyCode);
    }
}