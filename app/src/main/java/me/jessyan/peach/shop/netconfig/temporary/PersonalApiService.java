package me.jessyan.peach.shop.netconfig.temporary;

import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.DataBean;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.entity.user.BannerBean;
import me.jessyan.peach.shop.entity.user.IntegralProblemBean;
import me.jessyan.peach.shop.entity.user.InviteFriendBean;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.entity.user.ModifyBean;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * author: Created by HuiRan on 2017/11/24 10:29
 * E-Mail: 15260828327@163.com
 * description:
 */

public interface PersonalApiService {

    /**v
     * 启动页 - 广告图片
     */
    @POST("message/queryslideshowstartup")
    //Observable<ResponseBody> welcomeADImg();
    Observable<BasicResponse<BannerBean>> welcomeADImg();

    /**
     * 三方登录
     */
    @FormUrlEncoded
    @POST("user/weiXinLogin")
    Observable<BasicResponse<LoginBean>> thirdPartyLogin(@FieldMap Map<String, Object> map);

    /**
     * 手机登录
     */
    @FormUrlEncoded
    @POST("user/mobilelogin")
    Observable<BasicResponse<LoginBean>> mobileLogin(@FieldMap Map<String, Object> map);

    /**
     * 绑定手机
     */
    @FormUrlEncoded
    @POST("user/queryUserInsert")
    Observable<BasicResponse<LoginBean>> bindMobile(@FieldMap Map<String, Object> map);



    /**
     * 自动登录
     */
    @FormUrlEncoded
    @POST("user/automaticLogin")
    Observable<BasicResponse<LoginBean>> autoLogin(@Field("token") String token, @Field("deviceSerialId") String deviceSerialId);

    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("user/forgetPassword")
    Observable<BasicResponse<String>> forgetPassword(@Field("mobile") String mobile, @Field("password") String password, @Field("verifyCode") String verifyCode);

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("sms/sendSMS")
    Observable<BasicResponse<ResultBean>> getCode(@Field("mobile") String mobile);

    /**
     * 上传头像
     */
    @Multipart
    @POST("user/upload")
    Observable<BasicResponse<ResultBean>> changeIcon(@Part MultipartBody.Part file);

    /**
     * 修改昵称
     */
    @FormUrlEncoded
    @POST("user/update")
    Observable<BasicResponse<ResultBean>> changeNickname(@Field("nickname") String nickName);

    /**
     * 修改手机
     */
    @FormUrlEncoded
    @POST("user/bindingUpdate")
    Observable<BasicResponse<ModifyBean>> changeMobile(@Field("newMobile") String newMobile, @Field("mobile") String mobile, @Field("verifyCode") String verifyCode);

    /**
     * 绑定和修改支付宝
     */
    @FormUrlEncoded
    @POST("user/alipay/binding")
    Observable<BasicResponse<String>> modifyAlipay(@Field("alipayAccount") String alipayAccount, @Field("realName") String realName);

    /**
     * 积分宝问题
     */
    @FormUrlEncoded
    @POST("message/question")
    Observable<BasicResponse<IntegralProblemBean>> getIntegralProblem(@Field("type") int id);

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("user/suggestion")
    Observable<BasicResponse<String>> feedback(@Field("content") String content);

    /**
     * 查询该手机是否已经注册并存在于服务端
     */
    @FormUrlEncoded
    @POST("user/queryUserRelationship")
    Observable<BasicResponse<String>> queryMobile(@Field("mobile") String mobile);



    /**
     * 查询该手机是否已经注册并存在于服务端
     */
    @FormUrlEncoded
    @POST("user/queryUserRelationship")
    Observable<BasicResponse<String>> queryMobile(@Field("mobile") String mobile, @Field("loginType") int loginType);
    /**
     * 填写邀请码 / 关系绑定
     */
    @FormUrlEncoded
    @POST("user/updateUserInviter")
    Observable<BasicResponse<String>> fillInvitationCode(@Field("inviterCode") String inviteCode);

    /**
     * 获取分享的图片和信息
     */
    @FormUrlEncoded
    @POST("user/queryFenXiang")
    Observable<BasicResponse<InviteFriendBean>> getShareInfo(@Field("timestamp") long millis);

    /**
     * 查询邀请人数
     */
    @FormUrlEncoded
    @POST("user/querySumPeople")
    Observable<BasicResponse<DataBean>> queryInviteCount(@Field("timestamp") long millis);




}
