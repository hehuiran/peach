package me.jessyan.peach.shop.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;

/**
 * author: Create by HuiRan on 2018/12/31 下午3:24
 * email: 15260828327@163.com
 * description:
 */
public class OrderParentFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static OrderParentFragment newInstance(int type) {
        OrderParentFragment fragment = new OrderParentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentExtra.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_parent, container, false);
    }

    @Override
    public void initData() {

        Bundle bundle = getArguments();
        if (bundle!= null){
            int type = bundle.getInt(IntentExtra.TYPE);
        }

        String[] tabs = getResources().getStringArray(R.array.order_tab_array);
        int[] types = getResources().getIntArray(R.array.order_tab_type_array);
        OrderPagerAdapter pagerAdapter = new OrderPagerAdapter(getChildFragmentManager(), tabs, types);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    private static class OrderPagerAdapter extends FragmentPagerAdapter {

        private String[] mTabs;
        private int[] mTypes;

        private OrderPagerAdapter(FragmentManager fm, String[] tabs, int[] types) {
            super(fm);
            this.mTabs = tabs;
            this.mTypes = types;
        }

        @Override
        public Fragment getItem(int position) {
            return OrderSubFragment.newInstance(mTypes[position]);
        }

        @Override
        public int getCount() {
            return mTabs == null ? 0 : mTabs.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }
    }
}
