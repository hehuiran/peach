package me.jessyan.peach.shop.netconfig.temporary;


import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.user.MessageBean;
import me.jessyan.peach.shop.entity.goods.CollectionBean;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author: Created by HuiRan on 2018/5/4 16:38
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface CollectionApiService {

    /**
     * 收藏夹
     */
    @FormUrlEncoded
    @POST("follow/queryFollow")
    Observable<BasicResponse<CouponsCommodityBean>> getCollectionList(@Field("pageNo") int page, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("follow/queryFollow")
    Observable<ResponseBody> getCollectionList2(@Field("pageNo") int page, @Field("pageSize") int pageSize);



    /**
     * 收藏商品
     */
    @FormUrlEncoded
    @POST("follow/addFollow")
    Observable<BasicResponse<String>> collectionCommodity(@Field("itemid") String itemId);

    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("follow/deleteFollow")
    Observable<BasicResponse<String>> cancelCollectionCommodity(@Field("itemid") String itemId);

    /**
     * 收藏商品状态
     */
    @FormUrlEncoded
    @POST("follow/queryShopFollow")
    Observable<BasicResponse<String>> collectionCommodityStatus(@Field("itemid") String itemId);

    /**
     * 添加浏览足迹
     */
    @FormUrlEncoded
    @POST("goods/addUserFootprintln")
    Observable<BasicResponse<String>> postBrowseRecord(@Field("itemid") String itemId);

    /**
     * 删除浏览足迹
     */
    @FormUrlEncoded
    @POST("goods/deleteUserFootprint")
    Observable<BasicResponse<String>> deleteBrowseRecord(@Field("itemid") String itemId);

    /**
     * 浏览记录
     */
    @FormUrlEncoded
    @POST("goods/queryUserfootprint")
    Observable<BasicResponse<CollectionBean>> getBrowsingList(@Field("pageNo") int page, @Field("pageSize") int pageSize);

    /**
     * 添加优惠券
     */
    @FormUrlEncoded
    @POST("goods/addUserVoucher")
    Observable<BasicResponse<String>> addCouponRecord(@Field("itemid") String itemId, @Field("couponmoney") String couponMoney);

    /**
     * 领券记录
     */
    @FormUrlEncoded
    @POST("goods/queryUserVoucher")
    Observable<BasicResponse<CollectionBean>> getCouponRecordList(@Field("pageNo") int page, @Field("pageSize") int pageSize);

    /**
     * 删除券
     */
    @FormUrlEncoded
    @POST("goods/deleteUserVoucher")
    Observable<BasicResponse<String>> deleteCouponRecord(@Field("itemid") String itemId);

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("message/queryMessage")
    Observable<BasicResponse<MessageBean>> getMessageList(@Field("pageNo") int page, @Field("pageSize") int pageSize);
    @FormUrlEncoded
    @POST("message/queryMessage")
    Observable<ResponseBody> getMessageList2(@Field("pageNo") int page, @Field("pageSize") int pageSize);



    /**
     * 删除消息
     */
    @FormUrlEncoded
    @POST("message/updateMessage")
    Observable<BasicResponse<String>> deleteMessage(@Field("id") String id);

    /**
     *查询未读消息
     */
    @FormUrlEncoded
    @POST("message/queryMessageCount")
    Observable<BasicResponse<String>> queryUnreadMessage(@Field("timestamp") long millis);

    /**
     * 更改消息状态
     */
    @FormUrlEncoded
    @POST("message/updateUserMessage")
    Observable<BasicResponse<String>> updateMessageStatus(@Field("timestamp") long millis);

    /**
     * 查询收藏商品过期
     */
    @FormUrlEncoded
    @POST("follow/queryShopOverdue")
    Observable<BasicResponse<CouponsCommodityBean>> queryCollectionCommodityExpired(@Field("timestamp") long millis);





}
