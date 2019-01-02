package me.jessyan.peach.shop.netconfig.server;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.group.GroupBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author: Create by HuiRan on 2019/1/1 下午1:31
 * email: 15260828327@163.com
 * description:
 */
public interface GroupService {

    @FormUrlEncoded
    @POST("order/queryGroupbuying")
    Observable<BasicResponse<GroupBean>> getGroupList(@Field("pageNo") int page, @Field("pageSize") int pageSize);
}
