package me.jessyan.peach.shop.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.HomeCategoryOptionalBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface HomeCategoryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onGetHomeCategoryDataSuccess(HomeCategoryOptionalBean optionalBean);

        void onGetHomeCategoryDataFailed();

        void onLoadMoreGoodsSuccess(List<GoodsBean> list);

        void onLoadMoreGoodsFailed();

        void onGoodsRefreshCompleted(List<GoodsBean> list);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<HomeCategoryOptionalBean> getHomeCategoryData(int typeId, String oneType,
                                                                 String twoType, int selectType,
                                                                 String sort);

        Observable<CouponsCommodityBean> getGoods(int page, String oneType,
                                                  String twoType, int selectType,
                                                  String sort, String dataTimeStamp);
    }
}
