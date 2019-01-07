package me.jessyan.peach.shop.category.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.peach.shop.category.mvp.contract.CategorySubContract;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.home.CouponsCommodityBean;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.GoodsCategoryApiService;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 21:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CategorySubModel extends BaseModel implements CategorySubContract.Model {

    @Inject
    public CategorySubModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<CouponsCommodityBean> fetchCategorySubData(int page, String oneType,
                                                                 String twoType, String sort,
                                                                 String dataTimeStamp) {
        return mRepositoryManager
                .obtainRetrofitService(GoodsCategoryApiService.class)
                .getGoodsCategoryData(page,
                        CommonConstant.PAGE_SIZE,
                        oneType, twoType, sort, dataTimeStamp)
                .map(new ResponseFunction<>(CouponsCommodityBean.class));
    }
}