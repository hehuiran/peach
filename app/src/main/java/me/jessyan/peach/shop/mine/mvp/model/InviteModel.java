package me.jessyan.peach.shop.mine.mvp.model;


import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.user.InviteFriendBean;
import me.jessyan.peach.shop.mine.mvp.contract.InviteContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 13:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InviteModel extends BaseModel implements InviteContract.Model {

    @Inject
    public InviteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Optional<InviteFriendBean>> getInviteData() {
        return mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                .getShareInfo(System.currentTimeMillis())
                .compose(RxTransformer.handleResponse());
    }
}