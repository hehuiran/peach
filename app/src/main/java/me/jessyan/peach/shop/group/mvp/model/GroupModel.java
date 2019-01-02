package me.jessyan.peach.shop.group.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.group.GroupBean;
import me.jessyan.peach.shop.entity.group.GroupOptionalBean;
import me.jessyan.peach.shop.group.mvp.contract.GroupContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.server.ConfigService;
import me.jessyan.peach.shop.netconfig.server.GroupService;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class GroupModel extends BaseModel implements GroupContract.Model {

    @Inject
    public GroupModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GroupOptionalBean> getGroupData(int page, boolean pullToRefresh) {
        Observable<Optional<GroupBean>> groupObservable = mRepositoryManager.obtainRetrofitService(GroupService.class)
                .getGroupList(page, CommonConstant.PAGE_SIZE)
                .compose(RxTransformer.handleResponse());

        Observable<CouponsBannerBean> beanObservable = pullToRefresh ? mRepositoryManager.obtainRetrofitService(ConfigService.class)
                .getBanner("01")
                .map(new Function<BasicResponse<CouponsBannerBean>, CouponsBannerBean>() {
                    @Override
                    public CouponsBannerBean apply(BasicResponse<CouponsBannerBean> couponsBannerBeanBasicResponse) throws Exception {
                        CouponsBannerBean couponsBannerBean = couponsBannerBeanBasicResponse.getData();
                        if (couponsBannerBean == null) {
                            couponsBannerBean = new CouponsBannerBean();
                        }
                        return couponsBannerBean;
                    }
                }) : Observable.just(new CouponsBannerBean());

        return Observable.zip(groupObservable, beanObservable, new BiFunction<Optional<GroupBean>, CouponsBannerBean, GroupOptionalBean>() {
            @Override
            public GroupOptionalBean apply(Optional<GroupBean> groupBeanOptional, CouponsBannerBean couponsBannerBean) throws Exception {
                GroupOptionalBean optionalBean = new GroupOptionalBean();
                GroupBean groupBean = groupBeanOptional.getIncludeNull();
                if (groupBean != null) {
                    optionalBean.setGroupBean(groupBean);
                }

                optionalBean.setBannerList(couponsBannerBean.getBannerList());
                return optionalBean;
            }
        });
    }
}