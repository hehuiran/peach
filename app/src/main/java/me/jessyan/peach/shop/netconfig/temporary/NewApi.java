package me.jessyan.peach.shop.netconfig.temporary;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.DynamicBean;
import me.jessyan.peach.shop.entity.NavigationBarBean;
import me.jessyan.peach.shop.entity.SuccessBean;
import me.jessyan.peach.shop.entity.WebShipBean;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.SearchCommodityBean;
import me.jessyan.peach.shop.entity.goods.SuperBean;
import me.jessyan.peach.shop.entity.search.SearchHotBean;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dxp on 2018/9/18.
 * Describe : 后台联调新接口
 */

public interface NewApi {

    /**
     * 获取活动弹窗
     * 参数：type
     * 轮播图管理：01（首页轮播图）02（内页轮播图）
     * 弹窗管理：10（首页弹窗）
     * 广告管理：20（首页广告）21（启动页广告）22（我的页面广告
     */
    @FormUrlEncoded
    @POST("message/querySlideShow")
    Observable<BasicResponse<CouponsBannerBean>> getBanner(@Field("type") String type);

    @FormUrlEncoded
    @POST("message/querySlideShow")
    Observable<BasicResponse<CouponsBannerBean>> getCategoryBanner(@Field("type") String type, @Field("oneType") String oneType);


    /**
     * 获取应用底部导航栏数据
     */
    @GET("navigation/queryNavAllList")
    Observable<BasicResponse<List<NavigationBarBean>>> getNavigationBarData();


    /**
     * 获取不同模块下的广告小浮窗
     * ?type=11&position=首页
     */
    @GET("message/queryPagePop?type=11")
    Observable<BasicResponse<CouponsBannerBean>> getActiveSmallWindow(@Query("position") String position); //@Query("type") String type,


    /**
     * 查询9.9横向滚动商品
     * type=0
     */
    @FormUrlEncoded
    @POST("goods/queryBroadcast")
    Observable<ResponseBody> get99Goods(@Field("type") String type, @Field("pageSize") int pageSize, @Field("goods_category") int goods_category);

    /**
     * 首页播报接口
     * message/queryMessageBroadcast
     */
    @GET("message/queryMessageBroadcast")
    Observable<ResponseBody> getHomeReports();

    /**
     * 获取搜索页面 热门搜索词
     */
    @GET("user/getHotsearch")
    Observable<BasicResponse<SearchHotBean>> getHotSearchWords();


    /**
     * 获取超级入口
     *
     * @return
     */
    @GET("appMain/queryBrandLink")
    Observable<BasicResponse<List<SuperBean>>> getSuperManager();


    /**
     *
     */
    @GET("appMain/getItemCouponAndCommi")
    Observable<BasicResponse<WebShipBean>> getWebShip(@Query("itemId") String item_id, @Query("pid") String pid);


    /**
     * 发现链接
     */
    @GET("appMain/getSendCircle")
    Observable<BasicResponse<DynamicBean>> getDynamicData(@Query("type") int type, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);


    @GET("appMain/addSendCircleShareNum")
    Observable<BasicResponse<SuccessBean>> addShareNum(@Query("id") String id);


    @GET("shorten.json?source=211160679")
    Observable<ResponseBody> getUri(@Query("url_long") String url);


    @GET("user/validationTbAuthorization")
    Observable<ResponseBody> bindAli(@Query("tb_openid") String tb_openid);


    @GET("sug?code=utf-8")
    Observable<SearchCommodityBean> obtainSearch(@Query("q") String content);
}
