package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Space;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.mine.mvp.ui.fragment.IncomeFragment;

/**
 * author: Create by HuiRan on 2018/12/30 下午1:45
 * email: 15260828327@163.com
 * description:
 */
public class MyIncomeActivity extends BaseActivity {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, MyIncomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_income;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] tabs = getResources().getStringArray(R.array.income_tab_array);

        IncomePagerAdapter pagerAdapter = new IncomePagerAdapter(getSupportFragmentManager(), tabs);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView).init();
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @OnClick({R.id.iv_back, R.id.tv_instructions})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_instructions:
                break;
        }
    }

    private static class IncomePagerAdapter extends FragmentPagerAdapter {

        private String[] mTabs;

        private IncomePagerAdapter(FragmentManager fm, String[] tabs) {
            super(fm);
            this.mTabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            return IncomeFragment.newInstance(position);
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
