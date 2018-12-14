package me.jessyan.peach.shop.netconfig.temporary;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dxp on 2018/8/28.
 * Describe : 调试接口类
 */

public interface TestService {

    /**
     * 获取首页子分类
     * @param millis
     * @return
     */
    @FormUrlEncoded
    @POST("message/dataChannel")
    Observable<ResponseBody> getElectronicCommerce(@Field("timestamp") long millis);

    /**
     * 获取达人页面信息
     */
    @GET("talentInfo/getTalentInfo")
    Observable<ResponseBody> getTalentPageInfo(@Query("type") int type);

    @FormUrlEncoded
    @POST("user/queryUserTeam")
    Observable<ResponseBody> allianceTeamMine(@Field("timestamp") long millis);

    /**
     * 收益报表列表数据
     */
    @FormUrlEncoded
    @POST("order/withdraw/logger")
    Observable<ResponseBody> getIncomeReportsListData(@Field("pageNo") int pageNo, @Field("pageSize") int pageSize, @Field("type") int type);

    @FormUrlEncoded
    @POST("message/slideshow")
    Observable<ResponseBody> getBanner(@Field("type") String type);

    @FormUrlEncoded
    @POST("user/querySearch")
    Observable<ResponseBody> allianceListMine(@Field("type") int type, @Field("pageNo") int page, @Field("pageSize") int pageSize);


    /**
     * 查询用户收益列表
     * userId
     */
    @FormUrlEncoded
    @POST("order/queryOrderByUserId")
    Observable<ResponseBody> getUserProfitList(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("userId") String userId);

    /**
     * 获取首页导航条标题信息
     */
    @FormUrlEncoded
    @POST("goods/queryGoodsTypeOne")
    Observable<ResponseBody> getTitleData(@Field("timestamp") long millis);


    /**
     * 获取活动弹窗
     * 参数：type
     * 轮播图管理：01（首页轮播图）02（内页轮播图）
     * 弹窗管理：10（首页弹窗）
     * 广告管理：20（首页广告）21（启动页广告）22（我的页面广告
     */
    @FormUrlEncoded
    @POST("message/querySlideShow")
    Observable<ResponseBody> getBannerList(@Field("type") String type);


    /**
     * 获取首页分类fragment的子分类
     */
    @FormUrlEncoded
    @POST("goods/queryGoodsTwo")
    Observable<ResponseBody> getGridData(@Field("typeid") int typeId);

    /**
     * 获取应用底部导航栏数据
     */
    @GET("navigation/queryNavAllList")
    Observable<ResponseBody> getNavigationBarData();



}
