package me.jessyan.peach.shop.mine.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function3;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.mine.MineInfoUserIdentityBean;
import me.jessyan.peach.shop.entity.mine.MineOptionalBean;
import me.jessyan.peach.shop.entity.user.UserAccountBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.mine.mvp.contract.MineContract;
import me.jessyan.peach.shop.netconfig.temporary.MineApiService;
import me.jessyan.peach.shop.netconfig.temporary.NewApi;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<MineOptionalBean> getMineData() {
        Observable<BasicResponse<MineInfoUserIdentityBean>> userIdentity = mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .queryUserIdentity(System.currentTimeMillis());

        Observable<BasicResponse<UserAccountBean>> userSaveMoney = mRepositoryManager.obtainRetrofitService(MineApiService.class)
                .queryUserSaveMoney(System.currentTimeMillis());

        Observable<BasicResponse<CouponsBannerBean>> banner = mRepositoryManager.obtainRetrofitService(NewApi.class)
                .getBanner("22");
        return Observable.zip(userIdentity, userSaveMoney, banner, new Function3<BasicResponse<MineInfoUserIdentityBean>, BasicResponse<UserAccountBean>, BasicResponse<CouponsBannerBean>, MineOptionalBean>() {
            @Override
            public MineOptionalBean apply(BasicResponse<MineInfoUserIdentityBean> mineInfoUserIdentityBeanBasicResponse,
                                          BasicResponse<UserAccountBean> userAccountBeanBasicResponse,
                                          BasicResponse<CouponsBannerBean> couponsBannerBeanBasicResponse) throws Exception {
                if (mineInfoUserIdentityBeanBasicResponse.getCode() == 0) {
                    MineInfoUserIdentityBean.DataBean dataBean = mineInfoUserIdentityBeanBasicResponse.getData().getData().get(0);
                    UserInfo.getInstance().setIdentity(dataBean.getUsergrade());
                    UserInfo.getInstance().setSubordinate(StringUtils.parseInt(dataBean.getSubordinate()));
                    int status = UserInfo.getInstance().getIdentityStatus();
                    int newestIdentityStatus = StringUtils.parseInt(dataBean.getUseridentity());
                    if (status != newestIdentityStatus) {
                        UserInfo.getInstance().setIdentityStatus(newestIdentityStatus);
                        // TODO: 2018/12/27 身份发生了改变 之前是EventBus发送事件
                    }
                }

                MineOptionalBean optionalBean = new MineOptionalBean();

                if (userAccountBeanBasicResponse.getCode() == 0) {
                    UserAccountBean accountBean = userAccountBeanBasicResponse.getData();
                    optionalBean.setAccountBean(accountBean);
                }

                if (couponsBannerBeanBasicResponse.getCode() == 0) {
                    List<CouponsBannerBean.BannerBean> data = couponsBannerBeanBasicResponse.getData().getBannerList();
                    if (data != null && !data.isEmpty()) {
                        optionalBean.setBannerBean(data.get(0));
                    }
                }
                return optionalBean;
            }
        });
    }
}