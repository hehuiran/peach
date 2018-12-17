package me.jessyan.peach.shop.home.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.HomeCategoryOptionalBean;
import me.jessyan.peach.shop.home.mvp.contract.HomeCategoryContract;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.GoodsCategoryApiService;
import me.jessyan.peach.shop.netconfig.temporary.NewApi;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeCategoryModel extends BaseModel implements HomeCategoryContract.Model {

    @Inject
    public HomeCategoryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<HomeCategoryOptionalBean> getHomeCategoryData(int typeId, String oneType,
                                                                    String twoType, int selectType,
                                                                    String sort) {
        Observable<CouponsBannerBean> bannerObservable = mRepositoryManager
                .obtainRetrofitService(NewApi.class)
                .getCategoryBanner("02", oneType)
                .map(new ResponseFunction<>());

        Observable<GoodsCategoryGridBean> channelObservable = mRepositoryManager
                .obtainRetrofitService(GoodsCategoryApiService.class)
                .getGridData(typeId, CommonConstant.TYPE_CATEGORY_SECOND_TEN)
                .map(new ResponseFunction<>());


        return Observable.zip(bannerObservable, channelObservable,
                getGoods(CommonConstant.PAGE_INITIAL, oneType, twoType,
                        selectType, sort, CommonConstant.EMPTY_STRING),
                new Function3<CouponsBannerBean, GoodsCategoryGridBean, CouponsCommodityBean, HomeCategoryOptionalBean>() {
                    @Override
                    public HomeCategoryOptionalBean apply(CouponsBannerBean couponsBannerBean,
                                                          GoodsCategoryGridBean goodsCategoryGridBean,
                                                          CouponsCommodityBean couponsCommodityBean) throws Exception {
                        HomeCategoryOptionalBean optionalBean = new HomeCategoryOptionalBean();
                        optionalBean.setBannerData(couponsBannerBean);
                        optionalBean.setChannelData(goodsCategoryGridBean);
                        optionalBean.setCouponsCommodityBean(couponsCommodityBean);
                        return optionalBean;
                    }
                });
    }

    @Override
    public Observable<CouponsCommodityBean> getGoods(int page, String oneType,
                                                     String twoType, int selectType,
                                                     String sort, String dataTimeStamp) {
        return mRepositoryManager
                .obtainRetrofitService(GoodsCategoryApiService.class)
                .getGoodsCategoryData(page,
                        CommonConstant.PAGE_SIZE,
                        oneType, twoType, selectType, sort, dataTimeStamp)
                .map(new ResponseFunction<>())
                .map(new Function<CouponsCommodityBean, CouponsCommodityBean>() {
                    @Override
                    public CouponsCommodityBean apply(CouponsCommodityBean couponsCommodityBean) throws Exception {
                        List<GoodsBean> list = couponsCommodityBean.getList();
                        if (list.isEmpty()) {
                            GoodsBean bean = new GoodsBean();
                            bean.setItemType(RecyclerViewType.EMPTY_TYPE);
                            list.add(bean);
                        }
                        return couponsCommodityBean;
                    }
                });
    }
}