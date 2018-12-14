package me.jessyan.peach.shop.home.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function4;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.HomeMainOptionalBean;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.CouponsChannelBean;
import me.jessyan.peach.shop.entity.goods.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.goods.OrientationGoodsBean;
import me.jessyan.peach.shop.home.mvp.contract.HomeMainContract;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.GoodsCategoryApiService;
import me.jessyan.peach.shop.netconfig.temporary.NewApi;
import me.jessyan.peach.shop.netconfig.temporary.WillBuyApiService;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeMainModel extends BaseModel implements HomeMainContract.Model {

    @Inject
    public HomeMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<HomeMainOptionalBean> getHomeMainData() {
        Observable<CouponsBannerBean> bannerObservable = mRepositoryManager
                .obtainRetrofitService(NewApi.class)
                .getBanner("01")
                .map(new ResponseFunction<>());

        Observable<CouponsChannelBean> channelObservable = mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getElectronicCommerce(System.currentTimeMillis())
                .map(new ResponseFunction<>());

        Observable<OrientationGoodsBean> orientationObservable = mRepositoryManager
                .obtainRetrofitService(GoodsCategoryApiService.class)
                .getOrientationGoods("0", CommonConstant.PAGE_SIZE_ORIENTATION)
                .map(new ResponseFunction<>());

        return Observable.zip(bannerObservable, channelObservable,
                orientationObservable, getHostGoods(CommonConstant.PAGE_INITIAL),
                new Function4<CouponsBannerBean, CouponsChannelBean, OrientationGoodsBean, CouponsCommodityBean, HomeMainOptionalBean>() {
                    @Override
                    public HomeMainOptionalBean apply(CouponsBannerBean couponsBannerBean,
                                                      CouponsChannelBean couponsChannelBean,
                                                      OrientationGoodsBean orientationGoodsBean,
                                                      CouponsCommodityBean couponsCommodityBean) throws Exception {
                        HomeMainOptionalBean homeMainOptionalBean = new HomeMainOptionalBean();
                        homeMainOptionalBean.setBannerData(couponsBannerBean);
                        List<CouponsChannelBean.ChannelModel> channelBeanList = couponsChannelBean.getList();
                        if (channelBeanList != null && !channelBeanList.isEmpty()) {
                            homeMainOptionalBean.setChannelData(channelBeanList.get(0));
                        }
                        homeMainOptionalBean.setOrientationGoodsBean(orientationGoodsBean);
                        homeMainOptionalBean.setCouponsCommodityBean(couponsCommodityBean);
                        return homeMainOptionalBean;
                    }
                });
    }

    private Observable<CouponsCommodityBean> getHostGoods(int page) {
        return mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getCommodity(page, 7, CommonConstant.PAGE_SIZE, "首页")
                .map(new ResponseFunction<>());

    }
}