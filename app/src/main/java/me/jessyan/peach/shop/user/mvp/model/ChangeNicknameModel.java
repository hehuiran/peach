package me.jessyan.peach.shop.user.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.user.mvp.contract.ChangeNicknameContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/29/2018 22:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangeNicknameModel extends BaseModel implements ChangeNicknameContract.Model {

    @Inject
    public ChangeNicknameModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ResultBean> changeNickname(String nickname) {
        return mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                .changeNickname(nickname)
                .map(new ResponseFunction<>(ResultBean.class));
    }
}