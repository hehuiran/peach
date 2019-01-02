package me.jessyan.peach.shop.home.mvp.model;

import android.text.TextUtils;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailImageBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailInfoBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailOptionalBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailSellerBean;
import me.jessyan.peach.shop.entity.home.taobao.TaoBaoDetailsBean;
import me.jessyan.peach.shop.entity.home.taobao.TaoBaoImageBean;
import me.jessyan.peach.shop.entity.home.taobao.TaoBaoPriceBean;
import me.jessyan.peach.shop.home.mvp.contract.GoodsDetailContract;
import me.jessyan.peach.shop.netconfig.Optional;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.CollectionApiService;
import me.jessyan.peach.shop.netconfig.temporary.WillBuyApiService;
import me.jessyan.peach.shop.netconfig.transformer.RxTransformer;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/16/2018 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GoodsDetailModel extends BaseModel implements GoodsDetailContract.Model {

    @Inject
    Gson mGson;
    private final int mScreenWidth;

    @Inject
    public GoodsDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        mScreenWidth = ScreenUtils.getScreenWidth();
    }

    @Override
    public Observable<GoodsDetailOptionalBean> getGoodsDetailData(GoodsDetailConfigBean configBean) {
        String itemId = configBean.getItemId();
        boolean isRequestDetailsApi = configBean.isRequestDetailsApi();

        Observable<GoodsDetailOptionalBean> taoBaoObservable = getTaoBaoObservable(configBean);
        Observable<CouponsCommodityBean> ourServerGoodsDetailObservable = getOurServerGoodsDetail(itemId, isRequestDetailsApi);
        Observable<Boolean> goodsCollectionStatusObservable = getGoodsCollectionStatus(itemId);
        Observable<CouponsCommodityBean> recommendGoodsObservable = getRecommendGoods(itemId);

        return Observable.zip(taoBaoObservable, ourServerGoodsDetailObservable,
                goodsCollectionStatusObservable, recommendGoodsObservable, new Function4<GoodsDetailOptionalBean, CouponsCommodityBean, Boolean, CouponsCommodityBean, GoodsDetailOptionalBean>() {
                    @Override
                    public GoodsDetailOptionalBean apply(GoodsDetailOptionalBean goodsDetailOptionalBean,
                                                         CouponsCommodityBean ourServerGoodsDetailBean,
                                                         Boolean aBoolean,
                                                         CouponsCommodityBean recommendGoodsBean) throws Exception {
                        GoodsDetailInfoBean infoBean = goodsDetailOptionalBean.getGoodsDetailInfoBean();

                        List<GoodsBean> ourServerGoodsDetailList = ourServerGoodsDetailBean.getList();
                        if (!ourServerGoodsDetailList.isEmpty()) {
                            GoodsBean goodsBean = ourServerGoodsDetailList.get(0);
                            infoBean.setItemId(goodsBean.getItemId());
                            infoBean.setTitle(goodsBean.getTitle());
                            infoBean.setCouponEndTime(StringUtils.parseLong(goodsBean.getCouponEndTime()));
                            infoBean.setCouponStartTime(StringUtils.parseLong(goodsBean.getCouponStartTime()));
                            infoBean.setCouponMoney(goodsBean.getCouponmoney());
                            infoBean.setTkMoney(goodsBean.getTkMoney());
                            infoBean.setSoldCount(goodsBean.getSoldCount());
                            infoBean.setDiscountPrice(goodsBean.getDiscountPrice());
                            infoBean.setOriginalPrice(goodsBean.getOriginalPrice());
                            infoBean.setShopType(goodsBean.getShoptype());
                            infoBean.setGuideArticle(goodsBean.getGuideArticle());
                        }
                        infoBean.setCollection(aBoolean);

                        List<GoodsBean> recommendGoodsList = recommendGoodsBean.getList();
                        if (!recommendGoodsList.isEmpty()) {
                            goodsDetailOptionalBean.setRecommendList(recommendGoodsList);
                        }
                        return goodsDetailOptionalBean;
                    }
                });
    }

    @Override
    public Observable<BasicResponse<String>> collectionGoods(String itemId) {
        return mRepositoryManager.obtainRetrofitService(CollectionApiService.class)
                .collectionCommodity(itemId);
    }

    @Override
    public Observable<BasicResponse<String>> cancelCollectionGoods(String itemId) {
        return mRepositoryManager.obtainRetrofitService(CollectionApiService.class)
                .cancelCollectionCommodity(itemId);
    }

    @Override
    public Observable<Optional<ResultBean>> turnLink(String itemId, String pid) {
        return mRepositoryManager.obtainRetrofitService(WillBuyApiService.class)
                .turnLink(itemId, pid)
                .compose(RxTransformer.handleResponse());
    }

    private Observable<Boolean> getGoodsCollectionStatus(String itemId) {
        return mRepositoryManager
                .obtainRetrofitService(CollectionApiService.class)
                .collectionCommodityStatus(itemId)
                .map(new Function<BasicResponse<String>, Boolean>() {
                    @Override
                    public Boolean apply(BasicResponse<String> stringBasicResponse) throws Exception {
                        String data = stringBasicResponse.getData();
                        return !TextUtils.isEmpty(data) && data.equals("已收藏");
                    }
                });
    }

    private Observable<CouponsCommodityBean> getRecommendGoods(String itemId) {
        return mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getRecommendGoods(itemId, 8, 2)
                .map(new ResponseFunction<>(CouponsCommodityBean.class));
    }

    private Observable<CouponsCommodityBean> getOurServerGoodsDetail(String itemId, boolean isRequestDetail) {
        return isRequestDetail ?
                mRepositoryManager
                        .obtainRetrofitService(WillBuyApiService.class)
                        .getGoodsTitleDetails(itemId)
                        .map(new ResponseFunction<>(CouponsCommodityBean.class)) :
                Observable.just(new CouponsCommodityBean());
    }

    private Observable<GoodsDetailOptionalBean> getTaoBaoObservable(GoodsDetailConfigBean configBean) {
        Map<String, String> map = new HashMap<>();
        map.put("itemNumId", configBean.getItemId());
        String json = mGson.toJson(map);
        String data = json.replaceAll("[:]", EncodeUtils.urlEncode(":"));
        return mRepositoryManager
                .obtainRetrofitService(WillBuyApiService.class)
                .getTbGoodsDetails(data)
                .flatMap(new Function<TaoBaoDetailsBean, ObservableSource<GoodsDetailOptionalBean>>() {
                    @Override
                    public ObservableSource<GoodsDetailOptionalBean> apply(TaoBaoDetailsBean taoBaoDetailsBean) throws Exception {
                        GoodsDetailOptionalBean optionalBean = new GoodsDetailOptionalBean();

                        TaoBaoDetailsBean.DataBean data = taoBaoDetailsBean.getData();
                        TaoBaoDetailsBean.DataBean.ItemBean itemInfoBean = data.getItem();

                        List<String> picsPath = itemInfoBean.getImages();
                        //图片banner
                        List<String> bannerList = new ArrayList<>();
                        for (String s : picsPath) {
                            if (!TextUtils.isEmpty(s)) {
                                bannerList.add(getUrl(s));
                            }
                        }
                        optionalBean.setBannerList(bannerList);

                        //详情信息
                        GoodsDetailInfoBean infoBean = new GoodsDetailInfoBean();
                        List<TaoBaoDetailsBean.DataBean.ApiStackBean> apiStackList = data.getApiStack();
                        if (apiStackList != null && !apiStackList.isEmpty() && !TextUtils.isEmpty(apiStackList.get(0).getValue())) {
                            String value = apiStackList.get(0).getValue();
                            TaoBaoPriceBean taoBaoPriceBean = mGson.fromJson(value, TaoBaoPriceBean.class);
                            TaoBaoPriceBean.ItemBean itemBean = taoBaoPriceBean.getItem();
                            TaoBaoPriceBean.PriceBeanX priceBeanPrice = taoBaoPriceBean.getPrice();
                            //券后价
                            String originalPrice = priceBeanPrice.getPrice().getPriceText();
                            if (originalPrice.contains("-")) {
                                originalPrice = originalPrice.split("-")[0];
                            }
                            String discountPrice = originalPrice;
                            String couponMoney = configBean.getCouponMoney();
                            if (!TextUtils.isEmpty(couponMoney)) {
                                discountPrice = new BigDecimal(originalPrice).subtract(new BigDecimal(couponMoney)).toString();
                            }

                            infoBean.setSoldCount(itemBean.getSellCount());
                            infoBean.setDiscountPrice(StringUtils.filterDecimal(discountPrice));
                            infoBean.setOriginalPrice(StringUtils.filterDecimal(originalPrice));
                        } else {
                            infoBean.setSoldCount(configBean.getSellCount());
                            infoBean.setDiscountPrice(configBean.getDiscountPrice());
                            infoBean.setOriginalPrice(configBean.getOriginalPrice());
                        }
                        infoBean.setItemId(configBean.getItemId());
                        infoBean.setTitle(itemInfoBean.getTitle());
                        infoBean.setCouponEndTime(configBean.getCouponEndTime());
                        infoBean.setCouponStartTime(configBean.getCouponStartTime());
                        infoBean.setCouponMoney(configBean.getCouponMoney());
                        infoBean.setTkMoney(configBean.getTkMoney());

                        //店铺信息
                        GoodsDetailSellerBean goodsDetailSellerBean = data.getSeller();
                        goodsDetailSellerBean.setCreditLevelIcon(getUrl(goodsDetailSellerBean.getCreditLevelIcon()));
                        goodsDetailSellerBean.setShopIcon(getUrl(goodsDetailSellerBean.getShopIcon()));
                        infoBean.setShopType(goodsDetailSellerBean.getShopType());
                        optionalBean.setGoodsDetailInfoBean(infoBean);
                        optionalBean.setGoodsDetailSellerBean(goodsDetailSellerBean);

                        String requestImageUrl = itemInfoBean.getModuleDescUrl();

                        return TextUtils.isEmpty(requestImageUrl) ?
                                Observable.just(optionalBean) :
                                //请求淘宝图片详情
                                mRepositoryManager
                                        .obtainRetrofitService(WillBuyApiService.class)
                                        .getTbGoodsDetailsImage(getUrl(requestImageUrl))
                                        .map(new Function<TaoBaoImageBean, GoodsDetailOptionalBean>() {
                                            @Override
                                            public GoodsDetailOptionalBean apply(TaoBaoImageBean taoBaoImageBean) throws Exception {
                                                List<TaoBaoImageBean.DataBean.ChildrenBeanX> childrenBeanXList = taoBaoImageBean.getData().getChildren();
                                                if (childrenBeanXList != null && !childrenBeanXList.isEmpty()) {
                                                    List<GoodsDetailImageBean> imageList = new ArrayList<>();
                                                    for (TaoBaoImageBean.DataBean.ChildrenBeanX childrenBeanX : childrenBeanXList) {
                                                        TaoBaoImageBean.DataBean.ChildrenBeanX.ParamsBeanX params = childrenBeanX.getParams();
                                                        if (params != null) {
                                                            String picUrl = params.getPicUrl();
                                                            if (!TextUtils.isEmpty(picUrl)) {
                                                                TaoBaoImageBean.DataBean.ChildrenBeanX.ParamsBeanX.SizeBean sizeBean = params.getSize();
                                                                int width = StringUtils.parseInt(sizeBean.getWidth());
                                                                int height = StringUtils.parseInt(sizeBean.getHeight());

                                                                GoodsDetailImageBean goodsDetailImageBean = new GoodsDetailImageBean();
                                                                goodsDetailImageBean.setImgUrl(getUrl(picUrl));
                                                                goodsDetailImageBean.setWight(mScreenWidth);
                                                                goodsDetailImageBean.setHeight(mScreenWidth * height / width);
                                                                imageList.add(goodsDetailImageBean);
                                                            }
                                                        }
                                                    }
                                                    optionalBean.setImageList(imageList);
                                                }
                                                return optionalBean;
                                            }
                                        });
                    }
                });
    }

    private String getUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return url.startsWith(CommonConstant.HTTPS) ? url : CommonConstant.HTTPS.concat(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGson = null;
    }
}