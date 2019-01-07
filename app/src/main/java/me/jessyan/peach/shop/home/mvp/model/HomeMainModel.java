package me.jessyan.peach.shop.home.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function6;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.CouponsChannelBean;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.home.HomeMainOptionalBean;
import me.jessyan.peach.shop.entity.home.HomeSectionBean;
import me.jessyan.peach.shop.entity.home.OrientationGoodsBean;
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
                .map(new ResponseFunction<>(CouponsBannerBean.class));

        Observable<CouponsChannelBean> channelObservable = mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getElectronicCommerce(System.currentTimeMillis())
                .map(new ResponseFunction<>(CouponsChannelBean.class));

        Observable<CouponsBannerBean> advertisingObservable1 = mRepositoryManager
                .obtainRetrofitService(NewApi.class)
                .getBanner("20")
                .map(new ResponseFunction<>(CouponsBannerBean.class));

        Observable<CouponsBannerBean> advertisingObservable2 = mRepositoryManager
                .obtainRetrofitService(NewApi.class)
                .getBanner("20")
                .map(new ResponseFunction<>(CouponsBannerBean.class));

        Observable<OrientationGoodsBean> orientationObservable = mRepositoryManager
                .obtainRetrofitService(GoodsCategoryApiService.class)
                .getOrientationGoods("0", CommonConstant.PAGE_SIZE_ORIENTATION)
                .map(new ResponseFunction<>(OrientationGoodsBean.class));

        return Observable.zip(bannerObservable, channelObservable, advertisingObservable1, advertisingObservable2,
                orientationObservable, loadMoreGoods(CommonConstant.PAGE_INITIAL, CommonConstant.EMPTY_STRING),
                new Function6<CouponsBannerBean, CouponsChannelBean, CouponsBannerBean, CouponsBannerBean, OrientationGoodsBean, CouponsCommodityBean, HomeMainOptionalBean>() {
                    @Override
                    public HomeMainOptionalBean apply(CouponsBannerBean couponsBannerBean,
                                                      CouponsChannelBean couponsChannelBean,
                                                      CouponsBannerBean advertisingBean1,
                                                      CouponsBannerBean advertisingBean2,
                                                      OrientationGoodsBean orientationGoodsBean,
                                                      CouponsCommodityBean couponsCommodityBean) throws Exception {
                        HomeMainOptionalBean homeMainOptionalBean = new HomeMainOptionalBean();
                        homeMainOptionalBean.setBannerData(couponsBannerBean);
                        List<CouponsChannelBean.ChannelModel> channelBeanList = couponsChannelBean.getList();
                        if (channelBeanList != null && !channelBeanList.isEmpty()) {
                            homeMainOptionalBean.setChannelData(channelBeanList.get(0));
                        }
                        if (!advertisingBean1.getBannerList().isEmpty()) {
                            homeMainOptionalBean.setAdvertisingBean1(advertisingBean1.getBannerList().get(0));
                        }
                        if (!advertisingBean2.getBannerList().isEmpty()) {
                            homeMainOptionalBean.setAdvertisingBean2(advertisingBean2.getBannerList().get(0));
                        }
                        homeMainOptionalBean.setHomeSectionList(mockSectionData());
                        homeMainOptionalBean.setOrientationGoodsBean(orientationGoodsBean);
                        homeMainOptionalBean.setCouponsCommodityBean(couponsCommodityBean);
                        return homeMainOptionalBean;
                    }
                });
    }

    @Override
    public Observable<CouponsCommodityBean> loadMoreGoods(int page, String dataTimeStamp) {
        return mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getCommodity(page, 7, CommonConstant.PAGE_SIZE, "首页", dataTimeStamp)
                .map(new ResponseFunction<>(CouponsCommodityBean.class));

    }

    private List<HomeSectionBean> mockSectionData() {
        String[] titles = {"限量秒杀", "9块9包邮", "超人气专区", "积分兑换好礼"};
        String[] deses = {"限时限量抢购", "每天都有超低价", "高人气疯抢中", "畅享品质生活"};
        int[] imgs = {R.mipmap.ic_section_kill, R.mipmap.ic_section_nine, R.mipmap.ic_section_sentiment, R.mipmap.ic_section_integral};
        List<HomeSectionBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HomeSectionBean bean = new HomeSectionBean();
            bean.setId(i);
            bean.setTitle(titles[i]);
            bean.setDes(deses[i]);
            bean.setImg(imgs[i]);
            list.add(bean);
        }

        return list;
    }
}