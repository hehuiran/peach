package me.jessyan.peach.shop.home.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleMainBean;
import me.jessyan.peach.shop.home.mvp.ui.fragment.HomeCategoryFragment;
import me.jessyan.peach.shop.home.mvp.ui.fragment.HomeMainFragment;
import me.jessyan.peach.shop.vp.CommonPagerAdapter;

/**
 * author: Create by HuiRan on 2018/12/12 下午11:19
 * email: 15260828327@163.com
 * description:
 */
public class HomePagerAdapter extends CommonPagerAdapter<GoodsCategoryTitleBean.DataBean> {
    public HomePagerAdapter(FragmentManager manager, List<GoodsCategoryTitleBean.DataBean> mDatas) {
        super(manager, mDatas);
    }

    @Override
    protected Fragment getFragment(int position) {
        Fragment fragment;
        GoodsCategoryTitleBean.DataBean dataBean = mData.get(position);
        if (position != 0) {
            String oneType = CommonConstant.EMPTY_STRING, twoType = CommonConstant.EMPTY_STRING;
            GoodsCategoryTitleBean.DataBean.ExtraBean extra = dataBean.getExtra();
            if (extra != null) {
                oneType = extra.getOneType() != null ? extra.getOneType() : CommonConstant.EMPTY_STRING;
                twoType = extra.getTwoType() != null ? extra.getTwoType() : CommonConstant.EMPTY_STRING;
            }
            fragment = HomeCategoryFragment.newInstance(dataBean.getTypeid(), oneType, twoType);
        } else {
            GoodsCategoryTitleMainBean mainBean = (GoodsCategoryTitleMainBean) dataBean;
            fragment = HomeMainFragment.newInstance(mainBean.getGoodsTypeList());
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getTypename();
    }
}
