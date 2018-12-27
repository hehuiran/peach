package me.jessyan.peach.shop.netconfig.temporary;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.mine.AllianceManagementListMineResultBean;
import me.jessyan.peach.shop.entity.mine.AllianceManagementListOrderResultBean;
import me.jessyan.peach.shop.entity.mine.AllianceManagementTeamMineBean;
import me.jessyan.peach.shop.entity.mine.AllianceManagementTeamOrderBean;
import me.jessyan.peach.shop.entity.mine.DrawDetailsListBean;
import me.jessyan.peach.shop.entity.mine.IncomeReportsDetailsBean;
import me.jessyan.peach.shop.entity.mine.IncomeReportsListBean;
import me.jessyan.peach.shop.entity.mine.InviteRecordBean;
import me.jessyan.peach.shop.entity.mine.InviteTotalBean;
import me.jessyan.peach.shop.entity.mine.MineInfoAllianceBean;
import me.jessyan.peach.shop.entity.mine.MineInfoUserIdentityBean;
import me.jessyan.peach.shop.entity.mine.MineLoginCompletedBannerBean;
import me.jessyan.peach.shop.entity.mine.MineLoginCompletedLineBean;
import me.jessyan.peach.shop.entity.mine.MineLoginCompletedOrderBean;
import me.jessyan.peach.shop.entity.mine.OrderStatusBean;
import me.jessyan.peach.shop.entity.mine.WithDrawBean;
import me.jessyan.peach.shop.entity.user.UserAccountBean;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * author: Created by HuiRan on 2017/11/27 18:27
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface MineApiService {

    /**
     * 创建订单
     */
    @FormUrlEncoded
    @POST("order/create")
    Observable<BasicResponse<ResultBean>> createPaymentOrder(@Field("amount") String amount, @Field("type") int type);

    /**
     * 积分宝支付
     */
    @FormUrlEncoded
    @POST("order/pay")
    Observable<BasicResponse<String>> scorePayment(@Field("rechargeNo") String orderNumber);

    /**
     * 我的轮播
     */
    @FormUrlEncoded
    @POST("message/slideshow")
    Observable<BasicResponse<MineLoginCompletedBannerBean>> getBanner(@Field("type") int type);

    /**
     * 我的页面曲线
     */
    @FormUrlEncoded
    @POST("user/income/preview")
    Observable<BasicResponse<MineLoginCompletedLineBean>> getLine(@Field("timestamp") long millis);

    /**
     * 查询用户当前的身份
     */
    @FormUrlEncoded
    @POST("user/queryUserGrade")
    Observable<BasicResponse<MineInfoUserIdentityBean>> queryUserIdentity(@Field("timestamp") long millis);
    /*@FormUrlEncoded
    @POST("user/queryUserGrade")
    Observable<ResponseBody> queryUserIdentity2(@Field("timestamp") long millis);*/

    /**
     * 查询用户累计节省金额
     */
    @FormUrlEncoded
    @POST("user/queryAccumulative")
    Observable<BasicResponse<UserAccountBean>> queryUserSaveMoney(@Field("timestamp") long millis);


    /**
     *
     */

    /**
     * 我的页面联盟
     */
    @FormUrlEncoded
    @POST("user/queryInvitation")
    Observable<BasicResponse<MineInfoAllianceBean>> getAlliance(@Field("timestamp") long millis);

    /**
     * 获取我的页面最新订单信息
     */
    @FormUrlEncoded
    @POST("browse/queryOrderRecord")
    Observable<BasicResponse<MineLoginCompletedOrderBean>> getMineOrderDetails(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("type") int type);

    /**
     * 获取订单信息
     */
    @FormUrlEncoded
    @POST("browse/queryOrderRecord")
    Observable<BasicResponse<OrderStatusBean>> getOrderDetails(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("type") int type);

    /**
     * 获取余额
     */
    @FormUrlEncoded
    @POST("user/withdrawInfo")
    Observable<BasicResponse<WithDrawBean>> getBalance(@Field("timestamp") long millis);

    /**
     * 提现成功金额
     */
    @FormUrlEncoded
    @POST("user/queryExtractSuccess")
    Observable<BasicResponse<String>> getWithdrawMoney(@Field("timestamp") long millis);

    /**
     * 提交提现申请
     */
    @FormUrlEncoded
    @POST("order/withdraw")
    Observable<BasicResponse<String>> withDraw(@Field("amount") String amount, @Field("type") int type, @Field("mobile") String mobile, @Field("verifyCode") String verifyCode);

    /**
     * 提现记录
     */
    @FormUrlEncoded
    @POST("user/queryExtractRecord")
    Observable<BasicResponse<DrawDetailsListBean>> getWithDrawRecord(@Field("type") String type, @Field("pageNo") int page, @Field("pageSize") int pageSize);

    /**
     * 获取服务器时间戳
     */
    @FormUrlEncoded
    @POST("systemTime")
    Observable<BasicResponse<ResultBean>> getServiceTime(@Field("timestamp") long millis);


    /**
     * 邀请总人数
     */
    @FormUrlEncoded
    @POST("user/queryInviterCount")
    Observable<BasicResponse<String>> inviteTotalCount(@Field("timestamp") long millis);

    /**
     * 联盟管理我的团队人数
     */
    @FormUrlEncoded
    @POST("user/queryUserTeam")
    Observable<BasicResponse<AllianceManagementTeamMineBean>> allianceTeamMine(@Field("timestamp") long millis);


    /**
     * 联盟管理我的成员
     */
    @FormUrlEncoded
    @POST("user/querySearch")
    Observable<BasicResponse<AllianceManagementListMineResultBean>> allianceListMine(@Field("type") int type, @Field("pageNo") int page, @Field("pageSize") int pageSize);


    /**
     * 联盟管理团队订单数量
     */
    @FormUrlEncoded
    @POST("user/queryTeamOrder")
    Observable<BasicResponse<AllianceManagementTeamOrderBean>> allianceTeamOrder(@Field("timestamp") long millis);

    /**
     * 联盟管理订单列表
     */
    @FormUrlEncoded
    @POST("user/querySearch")
    Observable<BasicResponse<AllianceManagementListOrderResultBean>> allianceListOrder(@Field("type") int type, @Field("orderType") String orderType, @Field("pageNo") int page, @Field("pageSize") int pageSize);

    /**
     * 联盟管理搜索
     */
    @FormUrlEncoded
    @POST("user/querySearch")
    Observable<BasicResponse<AllianceManagementListMineResultBean>> allianceSearch(@Field("type") int type, @Field("content") String searchKey, @Field("pageNo") int page, @Field("pageSize") int pageSize);

    /**
     * 收益报表头部数据
     */
    @FormUrlEncoded
    @POST("order/queryProfit")
    Observable<BasicResponse<IncomeReportsDetailsBean>> getIncomeReportsDetails(@Field("timestamp") long millis);

    /**
     * 收益报表列表数据
     */
    @FormUrlEncoded
    @POST("order/withdraw/logger")
    Observable<BasicResponse<List<IncomeReportsListBean.DataModel>>> getIncomeReportsListData2(@Field("pageNo") int pageNo, @Field("pageSize") int pageSize, @Field("type") int type);


    /**
     * 收益报表列表数据
     */
    @FormUrlEncoded
    @POST("order/withdraw/logger")
    Observable<BasicResponse<IncomeReportsListBean>> getIncomeReportsListData(@Field("pageNo") int pageNo, @Field("pageSize") int pageSize, @Field("type") int type);


    /**
     * 瓜分红包金额
     */
    @FormUrlEncoded
    @POST("activity/redenvelope/home")
    Observable<BasicResponse<ResultBean>> getDivideMoney(@Field("timestamp") long millis);

    /**
     * 邀请记录
     */
    @FormUrlEncoded
    @POST("activity/redenvelope/record/invite")
    Observable<BasicResponse<InviteRecordBean>> getInviteRecord(@Field("pageNo") int page, @Field("pageSize") int pageSize);

    /**
     * 邀请记录上半部分
     */
    @FormUrlEncoded
    @POST("activity/redenvelope/record")
    Observable<BasicResponse<InviteTotalBean>> getInviteTotal(@Field("timestamp") long millis);

    /**
     * 上传解析出的订单json
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("goods/orderInsert")
    Observable<BasicResponse<String>> postJson(@Body RequestBody route);

    /**
     * 手动找回订单
     */
    @FormUrlEncoded
    @POST("orderBack/inserOrderBack")
    Observable<BasicResponse<String>> controlFindOrder(@Field("orderCode") String orderNumber);


    /**
     * 查询个人订单、收益总数
     */
    @FormUrlEncoded
    @POST("order/queryCountByUserId")
    Observable<ResponseBody> getUserOrderCountAndProfit(@Field("userId") String userId, @Field("type") int type);


    /**
     * 查询用户收益列表
     * userId
     */
    @FormUrlEncoded
    @POST("order/queryOrderByUserId")
    Observable<BasicResponse<IncomeReportsListBean>> getUserProfitList(@Field("pageNo") int page, @Field("pageSize") int pageSize, @Field("userId") String userId);


    /**
     * 修改用户微信号
     */
    @FormUrlEncoded
    @POST("user/updWXNumberById")
    Observable<ResponseBody> modifyWeiXinNum(@Field("wx_number") String wx_number);

    /**
     * 用于更新可见状态
     * visibility = 0 可见 ； 1 不可见
     */
    @FormUrlEncoded
    @POST("user/updVisibilityByUserId")
    Observable<ResponseBody> modifyWeiXinVisibility(@Field("visibility") int visibility);

    /**
     * 用于查询上级用户微信号
     */
    @GET("user/queryTopLevelByUserId")
    Observable<ResponseBody> getTopLevelWeiXin();


}
