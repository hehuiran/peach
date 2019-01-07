package me.jessyan.peach.shop.help;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.blankj.utilcode.util.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.category.mvp.ui.activity.CategorySubActivity;
import me.jessyan.peach.shop.entity.BannerCategoryClickBean;
import me.jessyan.peach.shop.entity.ExtraBean;
import me.jessyan.peach.shop.entity.event.ChangeTabEvent;
import me.jessyan.peach.shop.entity.goods.GiveGoodsDetailBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.home.mvp.ui.activity.GoodsDetailActivity;
import me.jessyan.peach.shop.home.mvp.ui.activity.SectionActivity;
import me.jessyan.peach.shop.home.mvp.ui.activity.WebActivity;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Create by HuiRan on 2019/1/7 下午2:47
 * email: 15260828327@163.com
 * description:
 */
public class BannerCategoryClickHelper {


    public BannerCategoryClickHelper() {

    }

    public void handleClick(Context context, BannerCategoryClickBean clickBean) {
        if (!LoginHelper.checkLogin(context)) {
            return;
        }
        String action = clickBean.getAction();
        if (action.equals("3")) {
            launcherLink(context, clickBean.getUrl(), clickBean.getTitle());
        } else if (action.equals("2")) {
            //do nothing
        } else {
            String type = clickBean.getType();
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case "0":
                        launcherGoodsDetail(context, clickBean.getGiveGoodsDetailBean());
                        break;
                    case "1":
                        launcherCategorySub(context, clickBean);
                        break;
                    case "2":
                        //do nothing
                        break;
                    case "3":
                        EventBus.getDefault().post(new ChangeTabEvent(StringUtils.parseInt(clickBean.getExtraBean().getTypeIndex())));
                        break;
                    case "4":
                        SectionActivity.launcher(context, ResourceUtils.getResourceString(R.string.high_coupon), 1);
                        break;
                    case "5":
                        SectionActivity.launcher(context, ResourceUtils.getResourceString(R.string.super_bargain), 7);
                        break;
                    case "6":
                        SectionActivity.launcher(context, ResourceUtils.getResourceString(R.string.nine_nine), 2);
                        break;
                }
            }
        }
    }

    private void launcherCategorySub(Context context, BannerCategoryClickBean clickBean) {
        ExtraBean extra = clickBean.getExtraBean();
        CategorySubActivity.launcher(context, clickBean.getTitle(), extra.getOneType(), extra.getTwoType());
    }

    private void launcherGoodsDetail(Context context, GiveGoodsDetailBean giveGoodsDetailBean) {
        GoodsDetailConfigBean configBean = GoodsDetailConfigBean.builder()
                .setRequestDetailsApi(false)
                .setTitle(giveGoodsDetailBean.getTitle())
                .setItemId(giveGoodsDetailBean.getItemId())
                .setCouponMoney(giveGoodsDetailBean.getCouponmoney())
                .setCouponEndTime(StringUtils.parseLong(giveGoodsDetailBean.getCouponEndTime()))
                .setCouponStartTime(StringUtils.parseLong(giveGoodsDetailBean.getCouponStartTime()))
                .build();
        GoodsDetailActivity.launcher(context, configBean);
    }

    private void launcherLink(Context context, String url, String title) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String suffix = "&type=0";
        if (url.endsWith("type=0")) {
            url = url.substring(0, url.length() - suffix.length());
            AlibcPage page = new AlibcPage(url);
            AliTradeHelper.getDefault().show(ActivityUtils.getTopActivity(), page, OpenType.Native);
        } else if (url.endsWith("type=1")) {
            url = url.substring(0, url.length() - suffix.length());
            url = url + "invitercode=" + UserInfo.getInstance().getInviteCode();
        } else if (url.endsWith("type=2")) {
            url = url.substring(0, url.length() - suffix.length());
            url = url + "userid=" + UserInfo.getInstance().getId();
        }
        WebActivity.launcher(context, title, url);
    }
}
