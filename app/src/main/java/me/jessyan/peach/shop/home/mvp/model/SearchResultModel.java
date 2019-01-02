package me.jessyan.peach.shop.home.mvp.model;

import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.search.SearchGoodsBean;
import me.jessyan.peach.shop.home.mvp.contract.SearchResultContract;
import me.jessyan.peach.shop.netconfig.temporary.WillBuyApiService;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/22/2018 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchResultModel extends BaseModel implements SearchResultContract.Model {

    private final SimpleDateFormat mSimpleDateFormat;

    @Inject
    public SearchResultModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public Observable<List<GoodsBean>> getSearchResult(int page, String keywords, String sort) {
        return mRepositoryManager.obtainRetrofitService(WillBuyApiService.class)
                .getSearchResult(page, CommonConstant.PAGE_SIZE, keywords, sort, 0)
                .map(new Function<BasicResponse<List<SearchGoodsBean>>, List<GoodsBean>>() {
                    @Override
                    public List<GoodsBean> apply(BasicResponse<List<SearchGoodsBean>> listBasicResponse) throws Exception {
                        List<GoodsBean> goodsData = new ArrayList<>();
                        List<SearchGoodsBean> data = listBasicResponse.getData();
                        if (data != null && !data.isEmpty()) {
                            for (SearchGoodsBean bean : data) {
                                GoodsBean item = new GoodsBean();
                                item.setImg(bean.getPict_url());
                                item.setTitle(bean.getTitle());

                                //满150.00元减5元
                                String coupon = bean.getCoupon_info();
                                if (TextUtils.isEmpty(coupon.trim())) {
                                    coupon = "0";
                                } else {
                                    coupon = coupon.substring(coupon.indexOf("减") + 1, coupon.length() - 1);
                                }

                                double c;
                                try {
                                    c = Double.valueOf(coupon);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    coupon = "0";
                                    c = 0;
                                }

                                String oldPrice, endPrice;
                                double p = StringUtils.sub(bean.getReserve_price(), bean.getZk_final_price());
                                boolean isOK = p == c;
                                if (c != 0 && !isOK) {
                                    //不是大淘客来源 做特殊处理
                                    oldPrice = StringUtils.filterDecimal(bean.getZk_final_price());
                                    endPrice = StringUtils.keepTwoDecimal(String.valueOf(Double.valueOf(oldPrice) - c));

                                    if (Double.valueOf(endPrice) <= 0) {
                                        //如果券后价小于等于0 则正常显示
                                        oldPrice = StringUtils.filterDecimal(bean.getReserve_price());
                                        endPrice = StringUtils.filterDecimal(bean.getZk_final_price());
                                    }

                                } else {
                                    oldPrice = StringUtils.filterDecimal(bean.getReserve_price());
                                    endPrice = StringUtils.filterDecimal(bean.getZk_final_price());
                                }


                                if (bean.getCommission_rate().isEmpty() || bean.getCommission_rate().equals("0")) {
                                    item.setTkMoney("0");
                                } else {
                                    double tk = (Double.valueOf(bean.getZk_final_price()) - c) * Double.valueOf(bean.getCommission_rate()) / 10000 * 0.18f;
                                    item.setTkMoney(StringUtils.keepTwoDecimal(String.valueOf(tk)));
                                }


                                item.setCouponmoney(coupon);
                                item.setOriginalPrice(oldPrice);
                                item.setDiscountPrice(endPrice);

                                item.setSoldCount(String.valueOf(bean.getVolume()));
                                item.setSellerId(String.valueOf(bean.getSeller_id()));
                                //0 = 淘宝 else 天猫
                                item.setShoptype(bean.getUser_type() == 0 ? "C" : "B");
                                item.setItemId(String.valueOf(bean.getNum_iid()));

                                //2018-08-09
                                if (!TextUtils.isEmpty(bean.getCoupon_start_time())) {
                                    if (bean.getCoupon_start_time().contains("-")) {
                                        item.setCouponStartTime(String.valueOf(TimeUtils.string2Millis(bean.getCoupon_start_time(), mSimpleDateFormat)));
                                    } else
                                        item.setCouponStartTime(String.valueOf(StringUtils.parseLong(bean.getCoupon_start_time()) * 1000L));

                                }

                                if (!TextUtils.isEmpty(bean.getCoupon_end_time())) {
                                    if (bean.getCoupon_end_time().contains("-")) {
                                        item.setCouponEndTime(String.valueOf(TimeUtils.string2Millis(bean.getCoupon_end_time(), mSimpleDateFormat)));
                                    } else
                                        item.setCouponEndTime(String.valueOf(StringUtils.parseLong(bean.getCoupon_end_time()) * 1000L));

                                }
                                item.setSource(0);

                                goodsData.add(item);
                            }
                        }
                        return goodsData;
                    }
                });
    }
}