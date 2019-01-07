package me.jessyan.peach.shop.netconfig.temporary;


import java.util.List;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ConfigBean;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.ShareBean;
import me.jessyan.peach.shop.entity.goods.ActiveImgBean;
import me.jessyan.peach.shop.entity.goods.CollectionBean;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.CouponsCategoryBean;
import me.jessyan.peach.shop.entity.goods.CouponsChannelBean;
import me.jessyan.peach.shop.entity.goods.FreeShippingOnePlusNWrapBean;
import me.jessyan.peach.shop.entity.goods.ProductDetailsIntervalBean;
import me.jessyan.peach.shop.entity.goods.VirtualMustSeeSectionBean;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.home.taobao.TaoBaoDetailsBean;
import me.jessyan.peach.shop.entity.home.taobao.TaoBaoImageBean;
import me.jessyan.peach.shop.entity.search.SearchGoodsBean;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * author Created by He on 2017/8/23.
 * 商品商店相关接口
 */

public interface WillBuyApiService {


    /**
     * app启动配置信息
     */
    @POST("config")
    Observable<BasicResponse<ConfigBean>> getConfig();

    /**
     * 获取需要分享出去的信息
     */
    @FormUrlEncoded
    @POST("shareConfig")
    Observable<BasicResponse<ShareBean>> getShareInfo(@Field("timestamp") long millis);

    /**
     * 淘宝转链接
     */
    @FormUrlEncoded
    @POST("goods/privilege")
    Observable<BasicResponse<ResultBean>> turnLink(@Field("itemId") String itemId, @Field("pid") String pid);
    /*@FormUrlEncoded
    @POST("goods/privilege")
    Observable<BasicResponse<ResultBean>> turnLink(@Field("itemId") String itemId, @Field("siteId") String siteId, @Field("adzoneId") String adzoneId, @Field("pid") String pid);*/

    /**
     * 首页轮播 : 01
     * 首页分类轮播： 02
     *
     */
    @FormUrlEncoded
    @POST("message/slideshow")
    Observable<BasicResponse<CouponsBannerBean>> getBanner(@Field("type") String type);

    @FormUrlEncoded
    @POST("message/slideshow")
    Observable<ResponseBody> getBanner2(@Field("type") int type);

    /**
     * 获取活动弹窗
     */
    @FormUrlEncoded
    @POST("message/slideshow")
    Observable<BasicResponse<CouponsBannerBean>> getActiveWindow(@Field("type") int type);

    /**
     * 首页轮播
     */
    @FormUrlEncoded
    @POST("message/slideshow")
    Observable<BasicResponse<ActiveImgBean>> getActivePic(@Field("type") int type);

    /**
     * 首页第三方渠道分类
     */
    @FormUrlEncoded
    @POST("message/dataChannel")
    Observable<BasicResponse<CouponsChannelBean>> getElectronicCommerce(@Field("timestamp") long millis);


    /**
     * 有券分类商品
     */
    @POST("message/goodsCustomType")
    Observable<BasicResponse<CouponsCategoryBean>> getCategory();

    /**
     * 今日必看
     */
    @POST("goods/queryHomepage")
    Observable<BasicResponse<VirtualMustSeeSectionBean>> getMustSeeSection();

    /**
     * 首页商品列表
     */
    @FormUrlEncoded
    @POST("goods/select")
    Observable<BasicResponse<CouponsCommodityBean>> getCommodity(@Field("pageNo") int page, @Field("selectType") int selectType,
                                                                 @Field("pageSize") int pageSize, @Field("type") String type,
                                                                 @Field("dataTimeStamp") String dataTimeStamp);


    /**
     * 每日神券头部商品
     */
    @FormUrlEncoded
    @POST("goods/queryEveryDayCommodity")
    Observable<BasicResponse<CouponsCommodityBean>> getGodCouponsHeaderCommodity(@Field("pageNo") int page, @Field("pageSize") int pageSize);


    /**
     * 秒杀中心
     */
    @FormUrlEncoded
    @POST("goods/Seckill")
    Observable<BasicResponse<CouponsCommodityBean>> getSecKillCommodity(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("type") int type, @Field("selectType") int selectType, @Field("keys") String keys);


    /**
     * 九块九包邮商品数据
     */
    @FormUrlEncoded
    @POST("goods/queryNine")
    Observable<BasicResponse<CouponsCommodityBean>> getFreeShippingCommodity(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("type") int type, @Field("selectType") int selectType);

    /**
     * 九块九包邮类别数据
     */
    @FormUrlEncoded
    @POST("goods/queryNineTop")
    Observable<BasicResponse<FreeShippingOnePlusNWrapBean>> getFreeShippingCategory(@Field("timestamp") long millis);

    /**
     * 九块九轮播
     */
    @FormUrlEncoded
    @POST("goods/queryCarousel")
    Observable<BasicResponse<VirtualMustSeeSectionBean>> getFreeShippingBanner(@Field("timestamp") long millis);

    /**
     * 今日必抢
     */
    @FormUrlEncoded
    @POST("goods/queryTodayRush")
    Observable<BasicResponse<CouponsCommodityBean>> getWillGrabCommodity(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("type") int type);

    /**
     * 边买边看
     */
    @FormUrlEncoded
    @POST("goods/queryBuySee")
    Observable<BasicResponse<CouponsCommodityBean>> getLookBuyCommodity(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("type") int type, @Field("selectType") int selectType);

    /**
     * 品牌上新banner
     */
    @FormUrlEncoded
    @POST("goods/queryBrandTop")
    Observable<BasicResponse<VirtualMustSeeSectionBean>> getNewBrandBanner(@Field("timestamp") long millis);

    /**
     * 品牌上新category
     */
    @FormUrlEncoded
    @POST("goods/queryBrandIn")
    Observable<BasicResponse<FreeShippingOnePlusNWrapBean>> getNewBrandCategory(@Field("timestamp") long millis);

    /**
     * 9.9等
     */
    @FormUrlEncoded
    @POST("goods/queryCommodity")
    Observable<BasicResponse<CouponsCommodityBean>> getSectionData(@Field("pageNo") int page, @Field("pageSize") int pageSize,
                                                                         @Field("sort") String sort,
                                                                         @Field("activitytype") int activityType);

    /**
     * 强烈推荐 way = bottom
     */
    @FormUrlEncoded
    @POST("goods/queryRecommend")
    Observable<BasicResponse<CouponsCommodityBean>> getHighlyRecommendByBottom(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("fqcat") String fq);

    @FormUrlEncoded
    @POST("goods/queryShopBanner")
    Observable<BasicResponse<VirtualMustSeeSectionBean>> getHighlyRecommendImg(@Field("shopname") String shopName);

    /**
     * 强烈推荐 way = top
     */
    @FormUrlEncoded
    @POST("goods/querytaobaoShopname")
    Observable<BasicResponse<CouponsCommodityBean>> getHighlyRecommendByTop(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("condition") String shopName);

    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("goods/detail")
    Observable<BasicResponse<CouponsCommodityBean>> getGoodsTitleDetails(@Field("itemId") String itemId);

    /**
     * 淘宝接口
     */
    @GET("https://h5api.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/")
    Observable<TaoBaoDetailsBean> getTbGoodsDetails(@Query(value = "data", encoded = true) String dataJson);

    /**
     * 淘宝图片详情接口
     */
    @GET
    Observable<TaoBaoImageBean> getTbGoodsDetailsImage(@Url String url);

    /**
     * 商品详情推荐商品列表
     */
    @FormUrlEncoded
    @POST("goods/queryRecommend")
    Observable<BasicResponse<CouponsCommodityBean>> getRecommendGoods(@Field("itemid") String itemId, @Field("count") int count, @Field("platform") int platform);


    @FormUrlEncoded
    @POST("goods/queryRecommend")
    Observable<BasicResponse<CollectionBean>> getSimilarData(@Field("itemid") String itemId, @Field("count") int count, @Field("platform") int platform);


    @FormUrlEncoded
    @POST("goods/queryRecommend")
    Observable<ResponseBody> getSimilarData2(@Field("itemid") String itemId, @Field("count") int count, @Field("platform") int platform);



    /**
     * 提交淘宝领券的url
     */
    @FormUrlEncoded
    @POST("goods/orderUrl")
    Observable<BasicResponse<String>> postCouponUrl(@Field("url") String url);


    /**
     * 商品详情轮询消息
     */
    @FormUrlEncoded
    @POST("goods/queryPopup")
    Observable<BasicResponse<ProductDetailsIntervalBean>> getProductInterval(@Field("timestamp") long millis);

    /**
     * 上传浏览记录
     */
    @FormUrlEncoded
    @POST("browse/browseAdd")
    Observable<BasicResponse<String>> postBrowsingHistory(@Field("commodity_name") String commodityName, @Field("shoptype") String shopType,
                                                          @Field("sellerId") String sellerId, @Field("Shop_name") String shopName, @Field("tkmoney") String tkmoney);

    /**
     * 获取搜索结果
     */
    @FormUrlEncoded
    @POST("goods/queryTaoBaoOptional")
    Observable<ResponseBody> getSearchResult2(@Field("pageNo") int page, @Field("pageSize") int pageSize
            , @Field("condition") String keywords, @Field("sort") String type, @Field("member") String member);


    @FormUrlEncoded
//    @POST("goods/queryHaoDanKu")
    @POST("goods/queryTaoBaoOptional")
    Observable<BasicResponse<List<SearchGoodsBean>>> getSearchResult(@Field("pageNo") int page, @Field("pageSize") int pageSize
            , @Field("condition") String keywords, @Field("sort") String sort, @Field("iscoupon") int iscoupon);

    /**
     * 查询商店商品列表
     */
    @FormUrlEncoded
    @POST("goods/queryShopid")
    Observable<BasicResponse<CouponsCommodityBean>> getShopGoods(@Field("sellerid") String sellerId, @Field("pageNo") int page, @Field("pageSize") int pageSize);





}
