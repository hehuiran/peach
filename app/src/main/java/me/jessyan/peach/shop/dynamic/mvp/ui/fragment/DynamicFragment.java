package me.jessyan.peach.shop.dynamic.mvp.ui.fragment;

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
import android.widget.Space;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import me.jessyan.peach.shop.R;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DynamicFragment extends BaseFragment {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static DynamicFragment newInstance() {
        DynamicFragment fragment = new DynamicFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }

    @Override
    public void initData() {
        String[] tabs = getResources().getStringArray(R.array.dynamic_tab_array);

        DynamicPagerAdapter pagerAdapter = new DynamicPagerAdapter(getChildFragmentManager(), tabs);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    private static class DynamicPagerAdapter extends FragmentPagerAdapter {

        private String[] mTabs;

        private DynamicPagerAdapter(FragmentManager fm, String[] tabs) {
            super(fm);
            this.mTabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            return DynamicSubFragment.newInstance(position);
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
