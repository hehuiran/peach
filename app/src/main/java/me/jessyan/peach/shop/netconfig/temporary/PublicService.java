package me.jessyan.peach.shop.netconfig.temporary;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.TalentBean;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dxp on 2018/8/27.
 * Describe :
 */

public interface PublicService {


    /**
     * 获取首页导航条标题信息
     */
    @GET("talentInfo/getTalentInfo")
    Observable<BasicResponse<TalentBean>> getTalentPageInfo(@Query("type") int type);



}
