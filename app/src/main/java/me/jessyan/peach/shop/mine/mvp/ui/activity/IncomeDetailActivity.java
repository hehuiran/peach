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
import android.widget.Space;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.mine.mvp.ui.fragment.IncomeDetailFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IncomeDetailActivity extends BaseActivity {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, IncomeDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_income_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] tabs = getResources().getStringArray(R.array.income_detail_tab_array);

        IncomeDetailPagerAdapter pagerAdapter = new IncomeDetailPagerAdapter(getSupportFragmentManager(), tabs);
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

    @Override
    public boolean useFragment() {
        return true;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    private static class IncomeDetailPagerAdapter extends FragmentPagerAdapter {

        private String[] mTabs;

        private IncomeDetailPagerAdapter(FragmentManager fm, String[] tabs) {
            super(fm);
            this.mTabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            return IncomeDetailFragment.newInstance(position);
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
