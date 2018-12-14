package me.jessyan.peach.shop.netconfig.temporary;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.goods.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryBannerBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.entity.goods.OrientationGoodsBean;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author: Created by HuiRan on 2017/12/11 10:52
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface GoodsCategoryApiService {

    /**
     * 获取首页导航条标题信息
     */
    @FormUrlEncoded
    @POST("goods/queryGoodsTypeOne")
    Observable<BasicResponse<GoodsCategoryTitleBean>> getTitleData(@Field("timestamp") long millis);

    /**
     * 获取首页分类fragment的轮播
     */
    @FormUrlEncoded
    @POST("goods/queryTemaibanner")
    Observable<BasicResponse<GoodsCategoryBannerBean>> getBannerData(@Field("timestamp") long millis);

    /**
     * 获取首页分类fragment的子分类
     * isDisAll -> 0或不传代表固定查询10个二级分类，传入1代表查询全部二级分类
     */
    @FormUrlEncoded
    @POST("goods/queryGoodsTwo")
    Observable<BasicResponse<GoodsCategoryGridBean>> getGridData(@Field("typeid") int typeId, @Field("isDisAll") int isDisAll);

    /**
     * 获取首页分类fragment的商品列表
     *
     */
    @FormUrlEncoded
    @POST("goods/querySpecialsale")
    Observable<BasicResponse<CouponsCommodityBean>> getGoodsCategoryData(@Field("pageNo") int page, @Field("pageSize")
            int pageSize, @Field("shoptype") String shoptype
            , @Field("typeTwo") String typeTwo, @Field("number") int number, @Field("sort") String sort);


    /**
     * 获取首页分类fragment的商品列表
     *
     */
    @FormUrlEncoded
    @POST("goods/querySpecialsale")
    Observable<BasicResponse<CouponsCommodityBean>> getGoodsCategoryData(@Field("pageNo") int page, @Field("pageSize")
            int pageSize, @Field("shoptype") String shoptype
            , @Field("typeTwo") String typeTwo, @Field("number") int number, @Field("sort") String sort
            , @Field("dataTimeStamp") String dataTimeStamp);


    /**
     * 获取首页分类fragment的推荐商品
     */
    @FormUrlEncoded
    @POST("goods/queryGoodsRecommend")
    Observable<BasicResponse<CouponsCommodityBean>> getGoodsCategoryRecommendData(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("goodstype") String typeName);


    /**
     * 获取分类页面的 横向滚动商品列表  12条
     */
    /*@FormUrlEncoded
    @POST("goods/queryBroadcast")
    Observable<BasicResponse<OrientationGoodsBean>> getOrientationGoods(@Field("type") String page, @Field("pageSize") int pageSize);*/

    @FormUrlEncoded
    @POST("goods/queryBroadcast")
    Observable<BasicResponse<OrientationGoodsBean>> getOrientationGoods(@Field("type") String type, @Field("pageSize") int pageSize);


    @FormUrlEncoded
    @POST("goods/getLikeGoods")
    Observable<ResponseBody> getOrientationGoods3(@Field("os") String os, @Field("ip") String ip, @Field("pageSize") int pageSize, @Field("pageNo") int pageNo);


}
