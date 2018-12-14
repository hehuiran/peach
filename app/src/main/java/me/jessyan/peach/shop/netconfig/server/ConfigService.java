package me.jessyan.peach.shop.netconfig.server;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author: Create by HuiRan on 2018/12/9 下午1:58
 * email: 15260828327@163.com
 * description:
 */
public interface ConfigService {
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
}
