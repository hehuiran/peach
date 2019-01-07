package me.jessyan.peach.shop.home.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.WebShipBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.home.mvp.contract.WebContract;
import me.jessyan.peach.shop.netconfig.temporary.NewApi;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/05/2019 21:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WebModel extends BaseModel implements WebContract.Model {

    @Inject
    public WebModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BasicResponse<WebShipBean>> getWebShip(String shipId) {
        return mRepositoryManager.obtainRetrofitService(NewApi.class)
                .getWebShip(shipId, UserInfo.getInstance().getPid());
    }
}