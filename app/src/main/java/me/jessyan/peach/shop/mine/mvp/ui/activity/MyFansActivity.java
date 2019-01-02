package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.mine.mvp.ui.fragment.MyFansSubFragment;
import me.jessyan.peach.shop.mine.mvp.ui.window.FansExpandWindow;

/**
 * 1 普通会员 2 超级粉丝 3 个体服务商 4 区县级服务商 5市级服务商  6  省级服务商   98 直接粉丝  99 推荐粉丝
 */
public class MyFansActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.iv_badge)
    ImageView mIvBadge;
    private String mFirstTabType = CommonConstant.EMPTY_STRING;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, MyFansActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_fans;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    showExpandWindow();
                    setBadgeLocation(false);
                }
            }
        });


        CharSequence[] tabs = getResources().getStringArray(R.array.my_fans_tab_array);
        String[] types = {"", "98", "99"};
        MyFansPagerAdapter pagerAdapter = new MyFansPagerAdapter(getSupportFragmentManager(), tabs, types);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mTabLayout.setViewPager(mViewPager);

        setBadgeLocation(true);
    }

    private void showExpandWindow() {
        FansExpandWindow fansExpandWindow = new FansExpandWindow(this);
        fansExpandWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBadgeLocation(true);
            }
        });
        fansExpandWindow.setOnExpandItemClickListener(new FansExpandWindow.OnExpandItemClickListener() {
            @Override
            public void onMoreChannelItemClick(String type, String name) {
                mFirstTabType = type;
                changeFirstTabName(name);
                refreshFirstFragment();
            }
        });
        fansExpandWindow.show(mTabLayout);
    }

    private void changeFirstTabName(String name) {
        TextView titleView = mTabLayout.getTitleView(0);
        if (titleView != null) {
            titleView.setText(name);
        }
    }

    private void refreshFirstFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            ((MyFansSubFragment) fragments.get(0)).setType(mFirstTabType);
        }
    }

    private void setBadgeLocation(boolean isDown) {
        int iconRes = isDown ? R.mipmap.ic_arrow_down_disable : R.mipmap.ic_arrow_up_enable;
        mIvBadge.setImageResource(iconRes);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), iconRes, options);
        final int iconHeight = options.outHeight;
        TextView titleView = mTabLayout.getTitleView(0);
        if (titleView != null) {
            titleView.post(() -> {
                if (mIvBadge.getVisibility() != View.VISIBLE) {
                    mIvBadge.setVisibility(View.VISIBLE);
                }
                int[] locations = new int[2];
                titleView.getLocationOnScreen(locations);
                int x = locations[0] + titleView.getWidth() + SizeUtils.dp2px(5);
                int y = (mTabLayout.getHeight() - iconHeight) / 2;
                mIvBadge.setTranslationX(x);
                mIvBadge.setTranslationY(y);
            });
        }
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

    @OnClick({R.id.iv_back, R.id.tv_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_rule:
                break;
        }
    }

    private static class MyFansPagerAdapter extends FragmentPagerAdapter {

        private CharSequence[] mTabs;
        private String[] mTypes;

        private MyFansPagerAdapter(FragmentManager fm, CharSequence[] tabs, String[] types) {
            super(fm);
            this.mTabs = tabs;
            this.mTypes = types;
        }

        @Override
        public Fragment getItem(int position) {
            return MyFansSubFragment.newInstance(mTypes[position]);
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
